package com.gmail.marcosav2010.api

import com.gmail.marcosav2010.myfitnesspal.api.LoginException
import com.gmail.marcosav2010.myfitnesspal.api.LoginHandler
import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

class SeleniumLoginHandler constructor(private val headless: Boolean = true) : LoginHandler {

    override fun login(url: String, username: String, password: String): Map<String, String> {
        val cookies = hashMapOf<String, String>()
        var driver: RemoteWebDriver? = null

        try {
            val options = FirefoxOptions()
            options.setHeadless(headless)
            driver = FirefoxDriver(options)
            driver[url]

            // Avoid rejecting cookies manually
            val expire = Date.from(LocalDate.now().plusYears(1).atStartOfDay(ZoneId.systemDefault()).toInstant())
            val cookieGdpr = Cookie("notice_gdpr_prefs", "0:", ".myfitnesspal.com", "/", expire, true)
            val cookiePrefs = Cookie("notice_preferences", "0:", ".myfitnesspal.com", "/", expire, true)

            driver.manage().addCookie(cookieGdpr)
            driver.manage().addCookie(cookiePrefs)
            driver.navigate().refresh()

            val emailField = driver.findElement(By.name(EMAIL_FIELD_NAME))
            val passwordField = driver.findElement(By.name(PASSWORD_FIELD_NAME))

            emailField.sendKeys(username.trim())
            passwordField.sendKeys(password)

            val lastUrl = driver.getCurrentUrl()
            val submit = driver.findElement(By.xpath(SUBMIT_BUTTON_XPATH))
            submit.click()

            WebDriverWait(driver, Duration.ofSeconds(LOGIN_WAIT_SECONDS.toLong()))
                .until { d: WebDriver -> !d.currentUrl.equals(lastUrl, ignoreCase = true) }

            val postLoginUrl = driver.getCurrentUrl()

            if (postLoginUrl.contains("error")) {
                val cause = postLoginUrl.split("error=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

                if (cause.size == 2 && cause[1].equals("CredentialsSignin", ignoreCase = true))
                    throw LoginException("Incorrect username or password")
                else
                    throw LoginException("Unable to sign in, try again later or with a different account")
            }

            driver.manage().cookies.forEach { c -> cookies[c.name] = c.value }

        } catch (ex: LoginException) {
            throw ex
        } catch (ex: TimeoutException) {
            throw LoginException("Timed out, log in took more than $LOGIN_WAIT_SECONDS seconds")
        } catch (ex: Exception) {
            throw LoginException("Unexpected error while logging in", ex)
        } finally {
            driver?.quit()
        }

        return cookies
    }

    companion object {

        init {
            System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver")
        }

        private const val LOGIN_WAIT_SECONDS = 10
        private const val EMAIL_FIELD_NAME = "email"
        private const val PASSWORD_FIELD_NAME = "password"
        private const val SUBMIT_BUTTON_XPATH = "//button[@type='submit']"
    }
}
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.21"
    application
    war
}

group = "com.gmail.marcosav2010"
version = "0.3"

repositories {
    mavenCentral()

    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/marcosav/myfitnesspal-api")
        credentials(PasswordCredentials::class)
    }
}

dependencies {
    testImplementation(kotlin("test"))

    implementation("com.gmail.marcosav2010:myfitnesspal-api:0.3.2")
    implementation("com.amazon.alexa:ask-sdk:2.43.6")
    implementation("javax.servlet:javax.servlet-api:4.0.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("com.gmail.marcosav2010.MFPSkillServlet")
}
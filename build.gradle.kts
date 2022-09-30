import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    application
    war
}

group = "com.gmail.marcosav2010"
version = "0.4"

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

    implementation("com.gmail.marcosav2010:myfitnesspal-api:0.5.1")
    implementation("com.amazon.alexa:ask-sdk:2.44.0")
    implementation("javax.servlet:javax.servlet-api:4.0.1")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.13.4")

    implementation("org.seleniumhq.selenium:selenium-java:4.4.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

application {
    mainClass.set("com.gmail.marcosav2010.MFPSkillServlet")
}

tasks.war {
    archiveFileName.set("myfitnesspal-alexa-skill.war")
}
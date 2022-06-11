import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.21"
    application

    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "com.gmail.marcosav2010"
version = "0.1"

tasks.withType<ShadowJar> {
    classifier = ""
}

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
    //implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
    //implementation("aws.sdk.kotlin:dynamodb:0.15.0")
    implementation("com.amazon.alexa:ask-sdk:2.43.5")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MFPSkillStreamHandler")
}
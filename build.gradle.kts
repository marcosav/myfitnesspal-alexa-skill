import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.23"
    application
    war
}

group = "com.gmail.marcosav2010"
version = "0.5.0"

val alexaSdkVersion = "2.86.0"
val jacksonVersion = "2.17.0"
val javaxServletApiVersion = "4.0.1"
val retrofitVersion = "2.11.0"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    implementation("com.amazon.alexa:ask-sdk:$alexaSdkVersion")
    implementation("javax.servlet:javax.servlet-api:$javaxServletApiVersion")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:$jacksonVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "21"
}

application {
    mainClass.set("com.gmail.marcosav2010.MFPSkillServlet")
}

tasks.war {
    archiveFileName.set("myfitnesspal-alexa-skill.war")
}
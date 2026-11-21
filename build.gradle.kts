import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.23"
    application
    war
}

group = "com.gmail.marcosav2010"
version = "0.5.0"

val alexaSdkVersion = "2.86.0"
val alexaModelVersion = "1.80.0"
val alexaModelRuntimeVersion = "1.0.6"
val jacksonVersion = "2.18.11"
val javaxServletApiVersion = "4.0.1"
val okhttpVersion = "4.12.0"
val retrofitVersion = "2.11.0"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

dependencyLocking {
    lockAllConfigurations()
}

dependencies {
    testImplementation(kotlin("test"))

    implementation("com.amazon.alexa:ask-sdk-core:$alexaSdkVersion")
    implementation("com.amazon.alexa:ask-sdk-servlet-support:$alexaSdkVersion")
    compileOnly("javax.servlet:javax.servlet-api:$javaxServletApiVersion")
    implementation(platform("com.fasterxml.jackson:jackson-bom:$jacksonVersion"))
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.squareup.okhttp3:okhttp:$okhttpVersion")
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-jackson:$retrofitVersion")

    constraints {
        implementation("com.amazon.alexa:ask-sdk-model:$alexaModelVersion")
        implementation("com.amazon.alexa:ask-sdk-model-runtime:$alexaModelRuntimeVersion")
        implementation("commons-io:commons-io:2.22.0")
    }
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
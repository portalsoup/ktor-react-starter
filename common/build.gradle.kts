plugins {
    application
    java
    kotlin("jvm") version "1.3.70"
    id("org.flywaydb.flyway") version "6.4.0"
}

val ktorVersion: String by rootProject

repositories {
    mavenCentral()
    jcenter()
}

dependencies {

    // core
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect", "1.2.51"))

    // logging
    implementation("org.slf4j:slf4j-api:1.7.30")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("ch.qos.logback:logback-core:1.2.3")

    implementation("io.jenetics:jpx:1.4.0")

}

val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

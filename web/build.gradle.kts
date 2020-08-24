import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    extra.apply {
        set("koin_version", "")
    }
}

plugins {
    application
    java
    kotlin("jvm") version "1.3.70"
    id("org.flywaydb.flyway") version "6.4.0"
}

// Variables
val exposedVersion: String by rootProject
val ktorVersion: String by rootProject

application {
    mainClassName = "com.portalsoup.ktorexposed.AppKt"
}

repositories {
    mavenCentral()
    jcenter()
}

// try koin for dependency injection
dependencies {

    implementation(project(":common"))
    implementation(project(":data"))

    implementation("io.jenetics:jpx:1.4.0")
    implementation("org.jetbrains.exposed", "exposed-core", exposedVersion)

    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-auth-jwt:$ktorVersion")
    implementation("io.ktor:ktor-gson:$ktorVersion")
    implementation("io.ktor:ktor-server-sessions:$ktorVersion")

    implementation("io.ktor:ktor-auth-jwt:$ktorVersion")

    // test
    testImplementation("junit:junit:4.12")
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

flyway {
    url = "jdbc:postgresql://db/pgdb"
    user = "bikes"
    password = "tour"
}
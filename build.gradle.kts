import org.jetbrains.kotlin.gradle.dsl.Coroutines
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
val exposedVersion: String by project
val test by lazy {
    null
}

application {
    mainClassName = "com.portalsoup.ktorexposed.AppKt"
}

repositories {
    mavenCentral()
    jcenter()
}

// try koin for dependency injection
dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testImplementation("junit:junit:4.12")
    implementation("ch.qos.logback:logback-classic:1.2.3")

//    implementation("io.ktor:ktor-server-core:1.3.2")
    implementation("io.ktor:ktor-server-netty:1.3.2")
    implementation("io.ktor:ktor-auth-jwt:1.3.2")
    implementation("io.ktor:ktor-gson:1.3.2")

    implementation("org.postgresql:postgresql:42.2.14")

    implementation("org.jetbrains.exposed", "exposed-core", exposedVersion)
    implementation("org.jetbrains.exposed", "exposed-dao", exposedVersion)
    implementation("org.jetbrains.exposed", "exposed-jdbc", exposedVersion)
    implementation("org.jetbrains.exposed", "exposed-java-time", exposedVersion)
    implementation("com.zaxxer:HikariCP:2.7.8")

    implementation("org.flywaydb:flyway-core:6.4.0")

//    implementation("org.koin:koin-core:2.0.0-GA2")
//    testimplementation("org.koin:koin-test:2.0.0-GA2")
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
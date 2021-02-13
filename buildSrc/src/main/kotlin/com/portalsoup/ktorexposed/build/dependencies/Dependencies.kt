package com.portalsoup.ktorexposed.build.dependencies

object Versions {
    const val jpx = "1.4.0"
    const val exposed = "0.26.2"
    const val hikari = "2.7.8"
    const val flyway = "6.4.0"
    const val ktor = "1.5.1"
    const val psql = "42.2.14"
    const val kotlinReflect = "1.2.51"
    const val slf4j = "1.7.30"
    const val logback = "1.2.3"

    // testing
    const val junit = "4.12"
    const val testng = "7.3.0"
    const val hamkrest = "1.7.0.3"
}

object Dependencies {

    object Subprojects {
        const val common = ":common"
        const val core = ":core"
        const val data = ":data"
    }

    val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
    val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlinReflect}"

    val jpx = "io.jenetics:jpx:${Versions.jpx}"
    val hikari = "com.zaxxer:HikariCP:${Versions.hikari}"
    val flywayCore = "org.flywaydb:flyway-core:${Versions.flyway}"

    val ktorNetty = "io.ktor:ktor-server-netty:${Versions.ktor}"
    val ktorGson = "io.ktor:ktor-gson:${Versions.ktor}"
    val ktorSeverSessions = "io.ktor:ktor-server-sessions:${Versions.ktor}"
    val ktorAuthJwt = "io.ktor:ktor-auth-jwt:${Versions.ktor}"

    val exposedCore = "org.jetbrains.exposed:exposed-core:${Versions.exposed}"
    val exposedDao = "org.jetbrains.exposed:exposed-dao:${Versions.exposed}"
    val exposedJdbc = "org.jetbrains.exposed:exposed-jdbc:${Versions.exposed}"
    val exposedJavaTime = "org.jetbrains.exposed:exposed-java-time:${Versions.exposed}"

    val psql = "org.postgresql:postgresql:${Versions.psql}"

    val slf4j = "org.slf4j:slf4j-api:${Versions.slf4j}"
    val logbackCore = "ch.qos.logback:logback-classic:${Versions.logback}"
    val logbackClassic = "ch.qos.logback:logback-core:${Versions.logback}"

    // testing
    val junit = "junit:junit:${Versions.junit}"
    val testng = "org.testng:testng:${Versions.testng}"
    val hamkrest = "com.natpryce:hamkrest:${Versions.hamkrest}"

}
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
    testCompile("junit:junit:4.12")
    compile("ch.qos.logback:logback-classic:1.2.3")

    compile("io.ktor:ktor-server-core:1.3.2")
    compile("io.ktor:ktor-server-jetty:1.3.2")
    compile("io.ktor:ktor-auth-jwt:1.3.2")
    compile("io.ktor:ktor-gson:1.3.2")

    compile("com.h2database:h2:1.4.200")

    compile("org.jetbrains.exposed", "exposed-core", "0.23.1")
    compile("org.jetbrains.exposed", "exposed-dao", "0.23.1")
    compile("org.jetbrains.exposed", "exposed-jdbc", "0.23.1")
    compile("com.zaxxer:HikariCP:2.7.8")

    compile("org.flywaydb:flyway-core:6.4.0")

//    compile("org.koin:koin-core:2.0.0-GA2")
//    testCompile("org.koin:koin-test:2.0.0-GA2")
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
    url = "jdbc:h2:./database/app"
    user = "tour"
    password = ""
}
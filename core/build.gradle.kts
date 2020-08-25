plugins {
    java
    kotlin("jvm") version "1.3.70"
}

val exposedVersion: String by rootProject
val ktorVersion: String by rootProject

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect", "1.2.51"))

    implementation(project(":common"))

    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-auth-jwt:$ktorVersion")

    testImplementation("org.testng:testng:7.3.0")
    testImplementation("com.natpryce:hamkrest:1.7.0.3")
}

val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

tasks.test {
    useTestNG() {
        useDefaultListeners = true
    }
}
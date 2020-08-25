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

    implementation(project(":common"))

    implementation("io.ktor:ktor-auth-jwt:$ktorVersion")

}

val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
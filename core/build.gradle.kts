import com.portalsoup.ktorexposed.build.dependencies.Constants
import com.portalsoup.ktorexposed.build.dependencies.Dependencies

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
    implementation(Dependencies.kotlinStdlib)
    implementation(Dependencies.kotlinReflect)

    implementation(project(Dependencies.Subprojects.common))
    implementation(project(Dependencies.Subprojects.data))

    implementation(Dependencies.jpx)
    implementation(Dependencies.ktorNetty)
    implementation(Dependencies.ktorAuthJwt)
    implementation(Dependencies.exposedCore)
    implementation(Dependencies.exposedDao)

    // test
    testImplementation(Dependencies.testng)
    testImplementation(Dependencies.hamkrest)
}

val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = Constants.jvmTarget
}
val compileTestKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = Constants.jvmTarget
}

tasks.test {
    useTestNG() {
        useDefaultListeners = true
    }
}
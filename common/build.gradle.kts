import com.portalsoup.ktorexposed.build.dependencies.Constants
import com.portalsoup.ktorexposed.build.dependencies.Dependencies

plugins {
    java
    kotlin("jvm") version "1.3.70"
}

val ktorVersion: String by rootProject

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(Dependencies.kotlinStdlib)
    implementation(Dependencies.kotlinReflect)

    implementation(Dependencies.slf4j)
    implementation(Dependencies.logbackClassic)
    implementation(Dependencies.logbackCore)

    implementation(Dependencies.ktorGson)
    implementation(Dependencies.ktorAuthJwt)
    implementation(Dependencies.jpx)

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

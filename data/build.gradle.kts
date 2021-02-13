import com.portalsoup.ktorexposed.build.dependencies.*

plugins {
    java
    kotlin("jvm") version "1.3.70"
    id("org.flywaydb.flyway") version "6.4.0"
}

val exposedVersion: String by rootProject
val ktorVersion: String by rootProject

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(Dependencies.kotlinStdlib)

    implementation(project(Dependencies.Subprojects.common))

    implementation(Dependencies.psql)
    implementation(Dependencies.exposedCore)
    implementation(Dependencies.exposedDao)
    implementation(Dependencies.exposedJavaTime)
    implementation(Dependencies.exposedJdbc)

    implementation(Dependencies.jpx)
    implementation(Dependencies.ktorAuthJwt)
}

val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = Constants.jvmTarget
}
val compileTestKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = Constants.jvmTarget
}

flyway {
    url = "jdbc:postgresql://db/pgdb"
    user = "bikes"
    password = "tour"
}

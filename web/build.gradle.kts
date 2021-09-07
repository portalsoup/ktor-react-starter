import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.portalsoup.ktorexposed.build.dependencies.*

plugins {
    application
    java
    kotlin("jvm") version "1.3.70"
    id("com.github.johnrengelman.shadow") version "4.0.4"
}

// Variables
val exposedVersion: String by rootProject
val ktorVersion: String by rootProject

application {
    mainClassName = "com.portalsoup.ktorreactstart.AppKt"
}

repositories {
    mavenCentral()
    jcenter()
}

// try koin for dependency injection
dependencies {
    implementation(project(Dependencies.Subprojects.core))

    implementation(Dependencies.slf4jApi)
    implementation(Dependencies.slf4jSimple)
    implementation(Dependencies.jpx)
    implementation(Dependencies.exposedCore)
    implementation(Dependencies.hikari)
    implementation(Dependencies.flywayCore)

    implementation(Dependencies.ktorAuthJwt)
    implementation(Dependencies.ktorGson)
    implementation(Dependencies.ktorSeverSessions)
    implementation(Dependencies.ktorNetty)

    // test
    testImplementation(Dependencies.junit)
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = Constants.jvmTarget
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = Constants.jvmTarget
}

tasks {
    named<ShadowJar>("shadowJar") {
        dependsOn(":client:copy")

        archiveBaseName.set("shadow")
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to application.mainClassName))
        }

    }
}

tasks.create("artifact") {
    dependsOn("shadowJar")
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    clean {
        delete("src/main/resources/static")
    }
}
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

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
    mainClassName = "com.portalsoup.ktorexposed.AppKt"
}

repositories {
    mavenCentral()
    jcenter()
}

// try koin for dependency injection
dependencies {

    implementation(project(":common"))
    implementation(project(":core"))

    implementation("io.jenetics:jpx:1.4.0")
    implementation("org.jetbrains.exposed", "exposed-core", exposedVersion)
    implementation("com.zaxxer:HikariCP:2.7.8")
    implementation("org.flywaydb:flyway-core:6.4.0")

    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-auth-jwt:$ktorVersion")
    implementation("io.ktor:ktor-gson:$ktorVersion")
    implementation("io.ktor:ktor-server-sessions:$ktorVersion")

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

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("shadow")
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to application.mainClassName))
        }

    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}
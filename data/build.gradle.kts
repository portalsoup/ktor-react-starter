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
    implementation(kotlin("stdlib-jdk8"))

    implementation(project(":common"))
    implementation(project(":core"))

    implementation("org.postgresql:postgresql:42.2.14")
    implementation("org.jetbrains.exposed", "exposed-core", exposedVersion)
    implementation("org.jetbrains.exposed", "exposed-dao", exposedVersion)
    implementation("org.jetbrains.exposed", "exposed-jdbc", exposedVersion)
    implementation("org.jetbrains.exposed", "exposed-java-time", exposedVersion)

    implementation("io.ktor:ktor-auth-jwt:$ktorVersion")
    implementation("io.jenetics:jpx:1.4.0")

}

val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

flyway {
    url = "jdbc:postgresql://db/pgdb"
    user = "bikes"
    password = "tour"
}

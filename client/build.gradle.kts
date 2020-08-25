plugins {
    id("com.github.node-gradle.node") version "2.2.4"
}

tasks.create<com.moowork.gradle.node.npm.NpmTask>("build") {
    dependsOn("npmInstall")
    setArgs(listOf("run", "dev"))
}
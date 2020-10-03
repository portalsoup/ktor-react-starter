plugins {
    id("com.github.node-gradle.node") version "2.2.4"
}

tasks.create<com.moowork.gradle.node.npm.NpmTask>("build") {
    dependsOn("npmInstall")
    setArgs(listOf("run", "build"))

    doLast {
        tasks["buildPermissions"]
    }
}

tasks.create<Exec>("buildPermissions") {
    commandLine("chmod", "-R", "0755", "$projectDir/build")
}
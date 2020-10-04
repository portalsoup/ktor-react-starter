plugins {
    id("com.github.node-gradle.node") version "2.2.4"
}

tasks["npmInstall"].let {
    it.inputs.files(
        "package.json",
        "package-lock.json",
        "webpack.config.js"
    )

    it.outputs.dir(
        "node_modules"
    )
}

tasks.create<com.moowork.gradle.node.task.NodeTask>("bundle") {
    dependsOn("npmInstall")

    inputs.dir(
        "$projectDir/src"
    )

    outputs.dir("$projectDir/build")

    // ./node_modules/webpack/bin/webpack.js --config webpack.config.js
    script = File("$projectDir/node_modules/webpack/bin/webpack.js")
    addArgs("--config", "webpack.config.js")
}

tasks.create<Copy>("copy") {
    dependsOn("bundle")

    inputs.dir(
        "$projectDir/build"
    )

    outputs.dir(
        "${rootProject.projectDir}/web/src/main/resources/assets"
    )

    from("build")
    into("${rootProject.projectDir}/web/src/main/resources/assets")
    include("**/*")
}

tasks.create<Delete>("clean") {
    delete("node_modules", "build")
}
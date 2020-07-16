
plugins {
    base
}

tasks.create<Exec>("npmInstall") {
    outputs.dir("node_modules")
    outputs.file("package-lock.json")
    inputs.file("package.json").withPathSensitivity(PathSensitivity.RELATIVE)

    workingDir(".")
    commandLine("npm", "ci")
}

tasks.create<Exec>("bundle") {
    dependsOn("npmInstall")
    
    inputs.file("package-lock.json").withPathSensitivity(PathSensitivity.RELATIVE)
    inputs.dir("src").withPathSensitivity(PathSensitivity.RELATIVE)
    inputs.file("webpack.config.js").withPathSensitivity(PathSensitivity.RELATIVE)
    outputs.dir("${project.buildDir}")


    workingDir("${project.rootDir}/client")
    executable("node")
    args(listOf("./node_modules/webpack/bin/webpack.js", "--config", "webpack.config.js"))
}


tasks.clean {
    doFirst {
        project.delete(project.fileTree("${project.rootDir}/client/dist") {
            exclude("ckeditor/")
            exclude(".gitkeep")
            exclude("logo.png")
        })
    }
}

tasks.create<Tar>("artifact") {
    dependsOn("bundle")

    inputs.dir("build").withPathSensitivity(PathSensitivity.RELATIVE)
    inputs.file("httpd.conf").withPathSensitivity(PathSensitivity.RELATIVE)

    outputs.file("../build/artifacts/client.zip")

    include("httpd.conf")
    include("build/*")
    include("build/*/*") //to include contents of a folder present inside Reports directory

    from("${project.rootDir}/client")
    baseName = "client"
    destinationDir = project.file("${project.rootDir}/build/artifacts")
    extension = "zip"
    compression = Compression.GZIP

    group = "tour"
}

tasks.create<Delete>("npmClean") {
    delete("node_modules")
}

tasks.clean {
    dependsOn("npmClean")
}
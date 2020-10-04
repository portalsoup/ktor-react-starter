tasks.create("deploy") {
    dependsOn("terraform", "ansible", ":client:copy", ":web:artifact")
}

tasks.create<Exec>("terraform") {
    dependsOn(":web:artifact")
    commandLine("echo", "TODO")
}

tasks.create<Exec>("ansible") {
    dependsOn(":web:artifact")
    commandLine("echo", "TODO")
}

tasks.create<Delete>("clean") {
    delete("build")
}
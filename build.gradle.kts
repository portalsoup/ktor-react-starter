tasks.create("deploy") {
    dependsOn("terraform", "ansible")
}

tasks.create<Exec>("terraform") {
    dependsOn(":web:shadowJar")
    commandLine("echo", "TODO")
}

tasks.create<Exec>("ansible") {
    dependsOn(":web:shadowJar")
    commandLine("echo", "TODO")
}

childProjects["web"]?.let { logger.info(it.tasks.joinToString(", ") { task -> task.name });it.tasks["shadowJar"] }?.mustRunAfter(":client:copy")

tasks.create<Delete>("clean") {
    delete("build")
}
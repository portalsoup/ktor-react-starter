tasks.create("deploy") {
    dependsOn("terraform", "ansible", ":client:copy", ":web:artifact")
}

tasks.create<Delete>("clean") {
    delete("build")
}
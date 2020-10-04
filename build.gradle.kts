tasks.create("prepareDeploy") {
    dependsOn(":client:copy")
}


tasks.create("deploy") {
    dependsOn("prepareDeploy", ":web:shadowJar")
}

tasks.create<Delete>("clean") {
    delete("build")
}
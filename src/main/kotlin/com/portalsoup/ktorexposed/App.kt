package com.portalsoup.ktorexposed

import com.google.gson.Gson
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.jetty.Jetty
import java.io.File
import java.lang.RuntimeException
import java.net.URL

fun main(args: Array<String>) {
    val config = getConfig()

    DatabaseFactory.init(config)

    embeddedServer(Jetty, commandLineEnvironment(args)).start(wait = true)
}

private fun getConfig(): AppConfig {
    val configUrl: URL = AppConfig::class.java.classLoader.getResource("config.json") ?: throw RuntimeException("failed to find config file")
    val jsonBuilder = StringBuilder()
    File(configUrl.file)
        .bufferedReader()
        .readLines()
        .forEach { line ->
            jsonBuilder.appendln("${line}\n")
        }
    val gson = Gson()
    return gson.fromJson(jsonBuilder.toString(), AppConfig::class.java)
}

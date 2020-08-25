package com.portalsoup.ktorexposed

import com.google.gson.Gson
import com.portalsoup.ktorexposed.core.DatabaseFactory
import com.portalsoup.ktorexposed.core.util.Retrier
import com.portalsoup.ktorexposed.resource.NewTravelerResource
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.lang.RuntimeException
import java.net.URL

fun main(args: Array<String>) {

    Retrier(
        "database initialization"
    ) {
        DatabaseFactory.init()
    }
    embeddedServer(Netty, commandLineEnvironment(args)).start(wait = true)
}

object Config {
    val global by lazy {
        getConfig()
    }

    private fun getConfig(): AppConfig {
        val config: String = NewTravelerResource::class.java.classLoader.getResourceAsStream("config.json")
            ?.bufferedReader()
            ?.use(BufferedReader::readText) ?: throw RuntimeException("failed to find config file")

        val gson = Gson()
        return gson.fromJson(config, AppConfig::class.java)
    }
}
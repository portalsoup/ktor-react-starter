package com.portalsoup.ktorexposed.core

import com.google.gson.Gson
import java.io.BufferedReader
import java.lang.RuntimeException

/**
 * Serialized from config.json.
 */
class AppConfig(val hostname: String, val server: Server, val db: Db)

data class Db(
    val username: String,
    val password: String,
    val jdbcUrl: String,
    val driverClassName: String,
    val isAutoCommit: Boolean,
    val maximumPoolSize: Int
)

data class Server(
    val publicIP: String,
    val publicPort: Int
)


object Config {
    val global by lazy {
        getConfig()
    }

    private fun getConfig(): AppConfig {
        val config: String = AppConfig::class.java.classLoader.getResourceAsStream("config.json")
            ?.bufferedReader()
            ?.use(BufferedReader::readText) ?: throw RuntimeException("failed to find config file")

        val gson = Gson()
        return gson.fromJson(config, AppConfig::class.java)
    }
}
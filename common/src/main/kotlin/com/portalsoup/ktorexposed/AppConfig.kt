package com.portalsoup.ktorexposed

import com.google.gson.Gson
import java.io.File
import java.lang.RuntimeException
import java.net.URL

/**
 * Serialized from config.json.
 */
class AppConfig(val hostname: String, val db: Db)

data class Db(
    val username: String,
    val password: String,
    val jdbcUrl: String,
    val driverClassName: String,
    val isAutoCommit: Boolean,
    val maximumPoolSize: Int
)

object Config {
    val global by lazy {
        getConfig()
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
}
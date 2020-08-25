package com.portalsoup.ktorexposed

import com.google.gson.Gson
import java.io.File
import java.lang.RuntimeException
import java.net.URL

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
    val publicPort: Int,
    val adminIP: String,
    val adminPort: Int
)
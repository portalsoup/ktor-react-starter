package com.portalsoup.ktorexposed

/**
 * Serialized from config.json.
 */
class AppConfig(val db: Db)

data class Db(val username: String, val password: String)
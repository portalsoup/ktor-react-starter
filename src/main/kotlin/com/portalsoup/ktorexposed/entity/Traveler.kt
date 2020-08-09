package com.portalsoup.ktorexposed.entity

import org.jetbrains.exposed.dao.id.IntIdTable

object Traveler : IntIdTable() {
    val email = varchar("email", 255)
    val passwordHash = varchar("passwordhash", 255)
    val passwordSalt = varchar("passwordsalt", 255)
}
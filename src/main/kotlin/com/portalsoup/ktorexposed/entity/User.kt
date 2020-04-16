package com.portalsoup.ktorexposed.entity

import io.ktor.auth.Principal
import org.jetbrains.exposed.dao.id.IntIdTable

object User : IntIdTable() {
    val email = varchar("firstname", 255)
    val passwordHash = varchar("lastname", 255)
    val passwordSalt = varchar("passwordsalt", 255)
}
package com.portalsoup.ktorexposed.entity

import org.jetbrains.exposed.dao.id.IntIdTable

object User : IntIdTable() {
    val firstName = varchar("firstname", 255)
    val lastName = varchar("lastname", 255)
}
package com.portalsoup.ktorexposed.entity

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Travelers : IntIdTable() {
    val email = varchar("email", 255)
    val passwordHash = varchar("passwordhash", 255)
    val passwordSalt = varchar("passwordsalt", 255)
}

class Traveler(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Traveler>(Travelers)

    var email by Travelers.email
    var passwordHash by Travelers.passwordHash
    var passwordSalt by Travelers.passwordSalt
}
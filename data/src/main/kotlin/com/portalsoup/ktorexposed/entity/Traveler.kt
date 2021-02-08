package com.portalsoup.ktorexposed.entity

import com.portalsoup.ktorexposed.resources.TravelerPrincipal
import com.portalsoup.ktorexposed.resources.TravelerResource
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object TravelerTable : IntIdTable() {
    val email = varchar("email", 255)
    val passwordHash = varchar("passwordhash", 255)
    val passwordSalt = varchar("passwordsalt", 255)
}

class Traveler(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Traveler>(TravelerTable)

    var email by TravelerTable.email
    var passwordHash by TravelerTable.passwordHash
    var passwordSalt by TravelerTable.passwordSalt
}

fun Traveler.toPrincipal(): TravelerPrincipal =
    TravelerPrincipal(
        id.value,
        email
    )
package com.portalsoup.ktorexposed.core.data.entity

import com.portalsoup.ktorexposed.core.resources.UserPrincipal
import com.portalsoup.ktorexposed.core.resources.UserResource
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object UserTable : IntIdTable(name = "USERACCOUNT") {
    val email = varchar("email", 255)
    val passwordHash = varchar("passwordhash", 255)
    val passwordSalt = varchar("passwordsalt", 255)
    val timesLoggedIn = long("times_logged_in")
}

class UserAccount(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserAccount>(UserTable)

    var email by UserTable.email
    var passwordHash by UserTable.passwordHash
    var passwordSalt by UserTable.passwordSalt
    var timesLoggedIn by UserTable.timesLoggedIn
}

fun UserAccount.toPrincipal(): UserPrincipal =
    UserPrincipal(
        id.value,
        email,
        timesLoggedIn
    )


fun UserAccount.toResource(): UserResource =
    UserResource(
        id.value,
        email,
        timesLoggedIn
    )
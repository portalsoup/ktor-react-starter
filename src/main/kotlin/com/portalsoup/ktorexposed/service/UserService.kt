package com.portalsoup.ktorexposed.service

import com.portalsoup.ktorexposed.api.resources.TravelerAuth
import com.portalsoup.ktorexposed.api.resources.toUserAuth
import com.portalsoup.ktorexposed.core.SecurePassword
import com.portalsoup.ktorexposed.entity.Traveler
import io.ktor.auth.UserPasswordCredential
import org.jetbrains.exposed.sql.select

// Resources
//data class FullNameResource(val firstName: String, val lastName: String) {
//    override fun toString(): String = "$firstName $lastName"
//}
//
//// Resource mapping functions
//private fun ResultRow.toFullName(): FullNameResource = FullNameResource(this[User.firstName], this[User.lastName])
//private fun UpdateStatement.fromFullName(fullName: FullNameResource) {
//    this[User.firstName] = fullName.firstName
//    this[User.lastName] = fullName.lastName
//}
//
//
//// Service functions
//fun fullName(id: Int): FullNameResource? = transaction {
//    User
//        .select { User.id eq id }
//        .single()
//        .toFullName()
//}
//
//fun updateFullName(id: Int, fullName: FullNameResource) {
//    User.update({User.id eq id}) { it: UpdateStatement -> it.fromFullName(fullName) }
//}
//
//fun listNames(): List<String> = transaction {
//    User.selectAll().map {
//        "${it[User.firstName]} ${it[User.lastName]}"
//    }
//}

fun checkAuth(credentials: UserPasswordCredential): TravelerAuth? {
    val foundUser = Traveler
        .select { Traveler.email eq credentials.name }
        .single()
        .toUserAuth()
    val generatedHash = SecurePassword(rawPassword = credentials.password, userSalt = foundUser.passwordSalt)
    return if (generatedHash.hashPassword().contentEquals(foundUser.passwordHash.toByteArray())) {
        foundUser
    } else {
        null
    }
}
package com.portalsoup.ktorexposed.service

import com.portalsoup.ktorexposed.api.resources.TravelerAuth
import com.portalsoup.ktorexposed.api.resources.TravelerAuthResource
import com.portalsoup.ktorexposed.api.resources.toUserAuth
import com.portalsoup.ktorexposed.core.SecurePassword
import com.portalsoup.ktorexposed.entity.Traveler
import io.ktor.auth.UserPasswordCredential
import org.jetbrains.exposed.sql.ResultRow
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

fun checkAuth(credentials: TravelerAuthResource): TravelerAuth? {
    val rawUser: ResultRow = Traveler
        .select { Traveler.email eq credentials.email }
        .single()

    println("this is the one ${rawUser}")
    val foundUser = rawUser.toUserAuth()
    val generatedHash = SecurePassword(rawPassword = credentials.password, userSalt = foundUser.passwordSalt)
    println("the salt ${foundUser.passwordSalt}")
    println("generated hash ${generatedHash.hashPassword()} existing hash ${foundUser.passwordHash}")
    return if (generatedHash.hashPassword().contentEquals(foundUser.passwordHash)) {
        foundUser
    } else {
        null
    }
}

fun ByteArray.toHexString() = asUByteArray().joinToString("") { it.toString(16).padStart(2, '0') }
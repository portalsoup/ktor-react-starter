package com.portalsoup.ktorexposed.api.resources

import com.portalsoup.ktorexposed.entity.User
import io.ktor.auth.Principal
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.ResultRow

data class UserAuth(
    val id: EntityID<Int>,
    val email: String,
    val passwordHash: String,
    val passwordSalt: ByteArray
) : Principal

// Resource mapping functions
fun ResultRow.toUserAuth(): UserAuth = UserAuth(this[User.id], this[User.email], this[User.passwordHash], this[User.passwordSalt].toByteArray())

data class SignupResource(val email: String, val password: String)
package com.portalsoup.ktorexposed.api.resources

import com.portalsoup.ktorexposed.entity.Traveler
import io.ktor.auth.Principal
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.ResultRow

data class TravelerAuth(
    val id: EntityID<Int>,
    val email: String,
    val passwordHash: String,
    val passwordSalt: ByteArray
) : Principal

// Resource mapping functions
fun ResultRow.toUserAuth(): TravelerAuth = TravelerAuth(this[Traveler.id], this[Traveler.email], this[Traveler.passwordHash], this[Traveler.passwordSalt].toByteArray())

data class SignupResource(val email: String, val password: String)
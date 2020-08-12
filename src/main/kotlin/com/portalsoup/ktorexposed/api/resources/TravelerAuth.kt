package com.portalsoup.ktorexposed.api.resources

import com.portalsoup.ktorexposed.entity.Traveler
import io.ktor.auth.Principal
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.ResultRow

data class TravelerAuth(
    val id: EntityID<Int>,
    val email: String,
    val passwordHash: String,
    val passwordSalt: String
) : Principal

// Resource mapping functions
fun ResultRow.toPrincipal(): TravelerAuth = TravelerAuth(this[Traveler.id], this[Traveler.email], this[Traveler.passwordHash], this[Traveler.passwordSalt])

data class TravelerAuthResource(val email: String, val password: String)
package com.portalsoup.ktorexposed.resources

import io.ktor.auth.*

data class TravelerPrincipal(
    val id: Int = -1,
    val email: String,
    val passwordHash: String,
    val passwordSalt: String
) : Principal
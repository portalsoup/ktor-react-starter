package com.portalsoup.ktorexposed.core.resources

data class UserResource(
    val id: Int? = null,
    val email: String,
    val timesLoggedIn: Long,
    val password: String? = null,
    val passwordHash: String? = null,
    val passwordSalt: String? = null
)
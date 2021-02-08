package com.portalsoup.ktorexposed.resources

data class TravelerResource(
    val id: Int = -1,
    val email: String,
    val password: String? = null,
    val passwordHash: String,
    val passwordSalt: String
)
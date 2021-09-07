package com.portalsoup.ktorexposed.core.resources

import io.ktor.auth.*

data class UserPrincipal(
    val id: Int = -1,
    val email: String,
    val timesLoggedIn: Long
) : Principal
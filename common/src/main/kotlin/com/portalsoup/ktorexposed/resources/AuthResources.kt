package com.portalsoup.ktorexposed.resources

import io.ktor.auth.Principal

data class EntityCreatedResource(val id: Int)

data class CurrentUserResource(val id: Int, val name: String) : Principal
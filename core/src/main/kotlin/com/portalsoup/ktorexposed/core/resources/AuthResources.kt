package com.portalsoup.ktorexposed.core.resources

import io.ktor.auth.Principal

data class EntityCreatedResource(val id: Int)

data class CurrentUserResource(val id: Int, val name: String, val timesLoggedIn: Long) : Principal

fun CurrentUserResource.toResource() = UserResource(id = id, email = name, timesLoggedIn = timesLoggedIn)
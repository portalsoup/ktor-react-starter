package com.portalsoup.ktorexposed

import com.portalsoup.ktorexposed.core.data.entity.UserTable
import com.portalsoup.ktorexposed.core.resources.UserPrincipal
import com.portalsoup.ktorexposed.core.resources.UserResource
import org.jetbrains.exposed.sql.ResultRow


fun ResultRow.toUser(): UserResource = UserResource(
    this[UserTable.id].value,
    this[UserTable.email],
    this[UserTable.timesLoggedIn],
    null,
    this[UserTable.passwordHash],
    this[UserTable.passwordSalt]
)

fun ResultRow.toPrincipal(): UserPrincipal = UserPrincipal(
    this[UserTable.id].value,
    this[UserTable.email],
    this[UserTable.timesLoggedIn]
)

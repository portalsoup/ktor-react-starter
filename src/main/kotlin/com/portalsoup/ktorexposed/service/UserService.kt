package com.portalsoup.ktorexposed.service

import com.portalsoup.ktorexposed.api.resources.TravelerAuth
import com.portalsoup.ktorexposed.api.resources.TravelerAuthResource
import com.portalsoup.ktorexposed.api.resources.toUserAuth
import com.portalsoup.ktorexposed.core.SecurePassword
import com.portalsoup.ktorexposed.entity.Traveler
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import java.lang.RuntimeException

fun checkAuth(credentials: TravelerAuthResource): TravelerAuth {
    val rawUser: ResultRow = Traveler
        .select { Traveler.email eq credentials.email }
        .single()

    val foundUser = rawUser.toUserAuth()
    val generatedHash = SecurePassword(rawPassword = credentials.password, userSalt = foundUser.passwordSalt)

    return foundUser.also {
        if (!generatedHash.hashPassword().contentEquals(it.passwordHash)) {
            throw AuthException("Wrong password")
        }
    }
}

fun ByteArray.toHexString() = asUByteArray().joinToString("") { it.toString(16).padStart(2, '0') }

data class AuthException(val reason: String = "Auth failed", val t: Throwable? = null): RuntimeException(reason, t)
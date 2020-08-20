package com.portalsoup.ktorexposed.service

import com.portalsoup.ktorexposed.api.resources.*
import com.portalsoup.ktorexposed.core.JwtCookie
import com.portalsoup.ktorexposed.core.JwtUtils
import com.portalsoup.ktorexposed.core.SecurePassword
import com.portalsoup.ktorexposed.entity.Traveler
import com.portalsoup.ktorexposed.entity.Travelers
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.RuntimeException

object UserService {
    fun signup(signupResource: TravelerAuthResource): EntityCreatedResource {
        val securePassword = SecurePassword(signupResource.password)
        val newId: Int = transaction {
            val inserted = Travelers.insert {
                it[email] = signupResource.email
                it[passwordHash] = securePassword.hashPassword()
                it[passwordSalt] = securePassword.userSalt
            }
            inserted get Travelers.id
        }.value
        return EntityCreatedResource(newId)
    }

    fun signin(credentials: TravelerAuthResource): JwtCookie {
        val user = transaction { checkAuth(credentials) }
        return JwtCookie(JwtUtils.makeToken(user))
    }

    fun currentUser(principal: JwtCookie): CurrentUserResource {
        val travelerAuth = principal.unpack()
        return CurrentUserResource(travelerAuth.id.value, travelerAuth.email)
    }

    fun checkAuth(credentials: TravelerAuthResource): TravelerAuth {
        val rawUser: ResultRow = Travelers
            .select { Travelers.email eq credentials.email }
            .single()

        val foundUser = rawUser.toPrincipal()
        val generatedHash = SecurePassword(rawPassword = credentials.password, userSalt = foundUser.passwordSalt)

        return foundUser.also {
            if (!generatedHash.hashPassword().contentEquals(it.passwordHash)) {
                throw AuthException("Wrong password")
            }
        }
    }
}

@ExperimentalUnsignedTypes
fun ByteArray.toHexString() = asUByteArray().joinToString("") { it.toString(16).padStart(2, '0') }

data class AuthException(val reason: String = "Auth failed", val t: Throwable? = null): RuntimeException(reason, t)
package com.portalsoup.ktorexposed.core.service

import com.portalsoup.ktorexposed.resources.*
import com.portalsoup.ktorexposed.dao.TravelerDAO
import com.portalsoup.ktorexposed.entity.TravelerTable
import com.portalsoup.ktorexposed.resources.TravelerPrincipal
import com.portalsoup.ktorexposed.core.util.JwtCookie
import com.portalsoup.ktorexposed.core.util.JwtUtils
import com.portalsoup.ktorexposed.core.util.SecurePassword
import com.portalsoup.ktorexposed.toPrincipal
import com.portalsoup.ktorexposed.toTraveler
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.RuntimeException

object UserService {
    fun signup(signupResource: TravelerResource): EntityCreatedResource {
        val securePassword = SecurePassword(
            signupResource.password ?: throw RuntimeException("Must contain password")
        )
        val newTraveler = transaction {
            TravelerDAO.create(
                TravelerResource(
                    email = signupResource.email,
                    passwordHash = securePassword.hashPassword(),
                    passwordSalt = securePassword.userSalt
                )
            )
        }
        return EntityCreatedResource(newTraveler.id.value)
    }

    fun signin(credentials: TravelerResource): JwtCookie {
        val user = transaction { checkAuth(credentials) }
        return JwtCookie(JwtUtils.makeToken(user))
    }

    fun currentUser(principal: JwtCookie): CurrentUserResource {
        val travelerAuth = principal.unpack()
        return CurrentUserResource(travelerAuth.id, travelerAuth.email)
    }

    private fun checkAuth(credentials: TravelerResource): TravelerPrincipal {
        val password = credentials.password ?: throw RuntimeException("Must contain password")
        val rawUser: ResultRow = TravelerTable
            .select { TravelerTable.email eq credentials.email }
            .single()

        // convert to traveler to verify
        val foundUser = rawUser.toTraveler()
        val generatedHash = SecurePassword(rawPassword = password, userSalt = foundUser.passwordSalt)

        foundUser.also {
            if (!generatedHash.hashPassword().contentEquals(it.passwordHash)) {
                throw AuthException("Wrong password")
            }
        }

        // verification succeeded so return it as principal
        return rawUser.toPrincipal()
    }
}

@ExperimentalUnsignedTypes
fun ByteArray.toHexString() = asUByteArray().joinToString("") { it.toString(16).padStart(2, '0') }

data class AuthException(val reason: String = "Auth failed", val t: Throwable? = null): RuntimeException(reason, t)
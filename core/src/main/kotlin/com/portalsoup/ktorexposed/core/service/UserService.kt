package com.portalsoup.ktorexposed.core.service

import com.portalsoup.ktorexposed.core.util.Try
import com.portalsoup.ktorexposed.resources.*
import com.portalsoup.ktorexposed.dao.TravelerDAO
import com.portalsoup.ktorexposed.entity.TravelerTable
import com.portalsoup.ktorexposed.resources.TravelerPrincipal
import com.portalsoup.ktorexposed.core.util.JwtCookie
import com.portalsoup.ktorexposed.core.util.JwtUtils
import com.portalsoup.ktorexposed.core.util.SecurePassword
import com.portalsoup.ktorexposed.entity.Traveler
import com.portalsoup.ktorexposed.toPrincipal
import com.portalsoup.ktorexposed.toTraveler
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.RuntimeException

object UserService {
    fun signup(signupResource: TravelerResource): Try<EntityCreatedResource> {
        val securePassword = SecurePassword(
            signupResource.password ?: throw RuntimeException("Must contain passw1ord")
        )

        return transaction {
            Traveler.find { TravelerTable.email eq signupResource.email }
                .firstOrNull()
                ?.let { Try.Failure(RuntimeException("Cannot create duplicate user")) }
                ?: TravelerDAO.create(
                    TravelerResource(
                        email = signupResource.email,
                        passwordHash = securePassword.hashPassword(),
                        passwordSalt = securePassword.userSalt
                    ))
                    .let { EntityCreatedResource(it.id.value) }
                    .let { Try.Success(it) }

//
//        val newTraveler = transaction {
//            TravelerDAO.create(
//                TravelerResource(
//                    email = signupResource.email,
//                    passwordHash = securePassword.hashPassword(),
//                    passwordSalt = securePassword.userSalt
//                )
//            )
//        }
//        return EntityCreatedResource(newTraveler.id.value)
        }
    }

    fun generateAuthCookie(credentials: TravelerResource): JwtCookie {
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
        val salt = foundUser.passwordSalt ?: throw RuntimeException("Salt needed to check auth")
        val generatedHash = SecurePassword(rawPassword = password, userSalt = salt)

        foundUser.also {
            val hash = it.passwordHash ?: throw RuntimeException("Hash needed to check auth")
            if (!generatedHash.hashPassword().contentEquals(hash)) {
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
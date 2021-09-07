package com.portalsoup.ktorexposed.core.service

import com.portalsoup.ktorexposed.core.util.Try
import com.portalsoup.ktorexposed.core.resources.*
import com.portalsoup.ktorexposed.core.data.dao.UserDAO
import com.portalsoup.ktorexposed.core.data.entity.UserTable
import com.portalsoup.ktorexposed.core.resources.UserPrincipal
import com.portalsoup.ktorexposed.core.util.JwtCookie
import com.portalsoup.ktorexposed.core.util.JwtUtils
import com.portalsoup.ktorexposed.core.util.SecurePassword
import com.portalsoup.ktorexposed.core.data.entity.UserAccount
import com.portalsoup.ktorexposed.toPrincipal
import com.portalsoup.ktorexposed.toUser
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.UserDataHolder
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.RuntimeException

object UserService {
    fun signup(signupResource: UserResource): Try<EntityCreatedResource> {
        val securePassword = SecurePassword(
            signupResource.password ?: throw RuntimeException("Must contain passw1ord")
        )

        return transaction {
            UserAccount.find { UserTable.email eq signupResource.email }
                .firstOrNull()
                ?.let { Try.Failure(RuntimeException("Cannot create duplicate user")) }
                ?: UserDAO.create(
                    UserResource(
                        email = signupResource.email,
                        passwordHash = securePassword.hashPassword(),
                        passwordSalt = securePassword.userSalt,
                        timesLoggedIn = 0
                    ))
                    .let { EntityCreatedResource(it.id.value) }
                    .let { Try.Success(it) }
        }
    }

    fun generateAuthCookie(credentials: UserResource): JwtCookie {
        val user = transaction { checkAuth(credentials) }
        return JwtCookie(JwtUtils.makeToken(user))
    }

    fun currentUser(principal: JwtCookie): CurrentUserResource {
        val userAuth = principal.unpack()
        return CurrentUserResource(userAuth.id, userAuth.email, userAuth.timesLoggedIn)
    }

    private fun checkAuth(credentials: UserResource): UserPrincipal {
        val password = credentials.password ?: throw RuntimeException("Must contain password")
        val rawUser: ResultRow = UserTable
            .select { UserTable.email eq credentials.email }
            .single()

        // convert to user to verify
        val foundUser = rawUser.toUser()
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

    fun incrementLoggedInCount(id: Int) {
        println("incrementing count for id $id")
        transaction { UserDAO.incrementLoggedInCount(id) }
    }
}

@ExperimentalUnsignedTypes
fun ByteArray.toHexString() = asUByteArray().joinToString("") { it.toString(16).padStart(2, '0') }

data class AuthException(val reason: String = "Auth failed", val t: Throwable? = null): RuntimeException(reason, t)
package com.portalsoup.ktorexposed.core

import com.auth0.jwt.*
import com.auth0.jwt.algorithms.*
import com.portalsoup.ktorexposed.Config
import com.portalsoup.ktorexposed.core.resource.TravelerPrincipal
import com.portalsoup.ktorexposed.dao.TravelerDAO
import com.portalsoup.ktorexposed.entity.Travelers
import com.portalsoup.ktorexposed.toPrincipal
import io.ktor.auth.Principal
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.NullPointerException
import java.lang.RuntimeException
import java.util.*

object JwtUtils {

    private const val secret = "zAP5MBA4B4Ijz0MZaS48"
//    internal const val issuer = "ktor.io"
    private const val validityInMs = 36_000_00 * 10 // 10 hours
    private val algorithm = Algorithm.HMAC512(secret)

    /**
     * Produce a token for this combination of User and Account
     */
    fun makeToken(user: TravelerPrincipal): String = JWT.create()
//        .withSubject("Authentication")
        .withIssuer(Config.global.hostname)
        .withClaim("id", user.id)
        .withExpiresAt(getExpiration())
        .sign(algorithm)

    fun verifyToken(): JWTVerifier = JWT
            .require(algorithm)
            .withIssuer(Config.global.hostname)
            .build()
    /**
     * Calculate the expiration Date based on current time + the given validity
     */
    private fun getExpiration() = Date(System.currentTimeMillis() + validityInMs)

}

class JwtCookie(val jwt: String): Principal {
    fun unpack(): TravelerPrincipal = transaction {
        val id = JwtUtils.verifyToken().verify(jwt).getClaim("id").asInt() ?: throw NullPointerException("No id claim found!")

        val foundTraveler = TravelerDAO.getWithAuth(id) ?: throw RuntimeException("No Traveler found")

        foundTraveler

        Travelers
            .select { Travelers.id eq id }
            .single()
            .toPrincipal()
    }
}

//data class UserPrincipal(val user: TravelerAuth): Principal
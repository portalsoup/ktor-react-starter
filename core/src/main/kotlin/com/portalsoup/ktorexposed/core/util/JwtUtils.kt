package com.portalsoup.ktorexposed.core.util

import com.auth0.jwt.*
import com.auth0.jwt.algorithms.*
import com.portalsoup.ktorexposed.Config
import com.portalsoup.ktorexposed.resources.TravelerPrincipal
import com.portalsoup.ktorexposed.dao.TravelerDAO
import com.portalsoup.ktorexposed.entity.TravelerTable
import com.portalsoup.ktorexposed.resources.TravelerResource
import com.portalsoup.ktorexposed.toPrincipal
import com.portalsoup.ktorexposed.utils.Logging
import com.portalsoup.ktorexposed.utils.getLogger
import com.portalsoup.ktorexposed.utils.log
import io.ktor.auth.Principal
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.NullPointerException
import java.lang.RuntimeException
import java.util.*
import java.util.logging.Logger


object JwtUtils: Logging {

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

class JwtCookie(val jwt: String): Principal, Logging {

    fun unpack(): TravelerPrincipal = transaction {
        JwtUtils
            .verifyToken()
            .verify(jwt)
            .getClaim("id")
            .asInt()
            .also { log().info("unpacking cookie... [$it]") }
            ?.let { TravelerDAO.getWithAuth(it) }
            ?: throw RuntimeException("No Traveler found!")
    }
}
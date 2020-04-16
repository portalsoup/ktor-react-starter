package com.portalsoup.ktorexposed.core


import com.auth0.jwt.*
import com.auth0.jwt.algorithms.*
import com.portalsoup.ktorexposed.api.resources.UserAuth
import com.portalsoup.ktorexposed.entity.User
import java.util.*

object JwtConfig {

    private const val secret = "zAP5MBA4B4Ijz0MZaS48"
    private const val issuer = "ktor.io"
    private const val validityInMs = 36_000_00 * 10 // 10 hours
    private val algorithm = Algorithm.HMAC512(secret)

    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()

    /**
     * Produce a token for this combination of User and Account
     */
    fun makeToken(user: UserAuth): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(issuer)
        .withClaim("password", user.passwordHash)
        .withExpiresAt(getExpiration())
        .sign(algorithm)

    /**
     * Calculate the expiration Date based on current time + the given validity
     */
    private fun getExpiration() = Date(System.currentTimeMillis() + validityInMs)

}
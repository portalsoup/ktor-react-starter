package com.portalsoup.ktorexposed.api

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.portalsoup.ktorexposed.api.routes.healthcheckRoutes
import com.portalsoup.ktorexposed.api.routes.authRoutes
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.auth.jwt.jwt
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.routing.routing

fun Application.main() {
    // Setup
    val jwtIssuer = "com.portalsoup"
    val jwtAudience = "whatthefuckamilol"

    val algorithm = Algorithm.HMAC256("secret")
    fun makeJwtVerifier(issuer: String, audience: String): JWTVerifier = JWT
        .require(algorithm)
//            .withAudience(audience) // figure this out later, should just be the app server's url?
        .withIssuer(issuer)
        .build()


    install(CallLogging)
    install(ContentNegotiation) { gson { } }
    install(Authentication) {
        jwt {
            realm = jwtIssuer
            verifier(makeJwtVerifier(jwtIssuer, jwtIssuer))
            validate { credential ->
                if (credential.payload.audience.contains(jwtAudience)) {
                    JWTPrincipal(credential.payload)
                } else {
                    println(credential.payload.audience.joinToString("\n"))
                    null
                }
            }
        }
    }

    routing {
        application.healthcheckRoutes()
        application.authRoutes()
    }
}


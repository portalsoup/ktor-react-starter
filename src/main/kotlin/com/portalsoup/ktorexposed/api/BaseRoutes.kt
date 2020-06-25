package com.portalsoup.ktorexposed.api

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.portalsoup.ktorexposed.api.routes.authedHealthcheck
import com.portalsoup.ktorexposed.api.routes.healthcheck
import com.portalsoup.ktorexposed.api.routes.login
import com.portalsoup.ktorexposed.api.routes.signup
import com.portalsoup.ktorexposed.core.JwtConfig
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.auth.jwt.jwt
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
import io.ktor.routing.Routing
import io.ktor.routing.routing

fun Application.main() {
    // Setup
    val jwtIssuer = "com.portalsoup"
    val jwtAudience = "whatthefuckamilol"

    val algorithm = Algorithm.HMAC256("secret")


    install(CallLogging)
    install(DefaultHeaders)
    install(ContentNegotiation) { gson { } }
    install(Authentication) {
        jwt { verifier(JwtConfig.verifier)
            realm = JwtConfig.issuer
            validate {
                with(it.payload) {
                    val email = getClaim("email").isNull
                    if (email)
                        null
                    else
                        JWTPrincipal(it.payload)
                }
            }
        }
//        jwt {
//            verifier(makeJwtVerifier(jwtIssuer, jwtIssuer))
//            realm = jwtIssuer
//            validate {
//                with(it.payload) {
//                    val login = getClaim("login").isNull
//                    val id = getClaim("id").isNull
//                    if (login || id)
//                        null
//                    else
//                        JWTPrincipal(it.payload)
//                }
//            }
//        }
    }

    install(Routing) {
        login()
        signup()
        healthcheck()
        authedHealthcheck()
    }
}


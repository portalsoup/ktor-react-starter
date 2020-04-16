package com.portalsoup.ktorexposed.api

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.portalsoup.ktorexposed.api.resources.SignupResource
import com.portalsoup.ktorexposed.core.JwtConfig
import com.portalsoup.ktorexposed.core.SecurePassword
import com.portalsoup.ktorexposed.entity.User
import com.portalsoup.ktorexposed.service.checkAuth
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.UserPasswordCredential
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.auth.jwt.jwt
import io.ktor.http.ContentType
import io.ktor.request.receive
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.math.sign


fun Application.main() {
    routing {


        // Setup
        val jwtIssuer = "com.portalsoup"
        val jwtAudience = "whatthefuckamilol"

        val algorithm = Algorithm.HMAC256("secret")
        fun makeJwtVerifier(issuer: String, audience: String): JWTVerifier = JWT
            .require(algorithm)
//            .withAudience(audience) // figure this out later, should just be the app server's url?
            .withIssuer(issuer)
            .build()

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

        // Status
        get("/status/healthcheck") {
            call.respondText("Yes, I live!", ContentType.Text.Plain)
        }

        post("signup") {
            println("entered closure")
            val signupResource = call.receive<SignupResource>()
            val securePassword = SecurePassword(signupResource.password)

            transaction {
                User.insert {
                    it[email] = signupResource.email
                    it[passwordHash] = securePassword.hashPassword().toString()
                    it[passwordSalt] = securePassword.userSalt.toString()
                }
            }
        }

        post("login") {
            val credentials: UserPasswordCredential = call.receive()
            val user = checkAuth(credentials)

            if (user != null) {
                val token = JwtConfig.makeToken(user)
                call.respondText(token)
            }
        }


//        authenticate {
//            route("")
//        }
    }
}
package com.portalsoup.ktorexposed.api

import com.auth0.jwt.algorithms.Algorithm
import com.portalsoup.ktorexposed.Config
import com.portalsoup.ktorexposed.api.resources.toUserAuth
import com.portalsoup.ktorexposed.api.routes.*
import com.portalsoup.ktorexposed.core.JwtUtils
import com.portalsoup.ktorexposed.core.UserPrincipal
import com.portalsoup.ktorexposed.entity.Traveler
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.auth.jwt.jwt
import io.ktor.features.*
import io.ktor.gson.gson
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.routing.Routing
import io.ktor.sessions.SessionStorageMemory
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.RuntimeException

fun Application.main() {
    // Setup
    val jwtIssuer = "com.portalsoup"
    val jwtAudience = "whatthefuckamilol"

    val algorithm = Algorithm.HMAC256("secret")

    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Get)
        method(HttpMethod.Post)

        header("Content-Type")
        header(HttpHeaders.Accept)
        header(HttpHeaders.AcceptEncoding)
        header(HttpHeaders.AcceptLanguage)
        header(HttpHeaders.AccessControlRequestHeaders)
        header(HttpHeaders.AccessControlRequestMethod)
        header(HttpHeaders.Connection)
        header(HttpHeaders.Host)
        header(HttpHeaders.Origin)
        header(HttpHeaders.Referrer)
        header(HttpHeaders.UserAgent)
        header(HttpHeaders.AccessControlAllowOrigin)

        host("localhost")
//        anyHost()

        allowSameOrigin = true

        allowCredentials = true
    }

    install(Sessions) {
        cookie<MySession>(
            "SESSION",
            SessionStorageMemory() // make redis
        ) {
            cookie.path = "/"
            cookie.extensions["SameSite"] = "lax"
        }
    }

    install(CallLogging)

    install(DefaultHeaders)

    install(ContentNegotiation) { gson { } }

    install(Authentication) {
        jwt(name = "user") {
            println("in jwt block")
            verifier(JwtUtils.verifyToken())
            realm = Config.global.hostname
            validate {
                println("validating... $it")
                with(it.payload) {
                    val email = getClaim("email").asString() ?: ""
                    when {
                        email.isNotEmpty() -> transaction {
                            Traveler
                                .select { Traveler.email eq email }
                                .single()
                                .toUserAuth()
                        }.let { user -> UserPrincipal(user) }
                        else -> null
                    }
                }
            }
        }
    }

    install(Routing) {
        user()
        healthcheck()
        authedHealthcheck()
        coordinates()
    }
}

data class MySession(val id: Int, val jwt: String)

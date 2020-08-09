package com.portalsoup.ktorexposed.api

import com.auth0.jwt.algorithms.Algorithm
import com.portalsoup.ktorexposed.api.routes.*
import com.portalsoup.ktorexposed.core.JwtConfig
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.auth.jwt.jwt
import io.ktor.features.*
import io.ktor.gson.gson
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie
import java.time.Duration

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
        cookie<MySession>("SESSION")
    }

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
    }

    install(Routing) {
        login()
        signup()
        healthcheck()
        authedHealthcheck()
        coordinates()


        route("/test") {
            get {
                call.respondText("POWER OVERWHELMING", ContentType.Text.Plain)
            }
        }
    }
}

data class MySession(val name: String, val jwt: String)

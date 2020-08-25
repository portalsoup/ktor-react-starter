package com.portalsoup.ktorexposed.api

import com.portalsoup.ktorexposed.api.routes.*
import com.portalsoup.ktorexposed.core.JwtCookie
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.authentication
import io.ktor.auth.session
import io.ktor.features.*
import io.ktor.gson.gson
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.sessions.*
import com.portalsoup.ktorexposed.api.routes.coordinates

fun Application.main() {
    // Setup
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
        header(HttpHeaders.Cookie)

        host("localhost")

        allowSameOrigin = true

        allowCredentials = true
    }

    install(Sessions) {
        cookie<JwtCookie>("auth") {
            cookie.path = "/"
            cookie.extensions["SameSite"] = "lax"
        }
    }

    install(CallLogging)

    install(DefaultHeaders)

    install(ContentNegotiation) { gson { } }

    install(Authentication) {
        session<JwtCookie> {
            challenge {
                println("Can find cookie? ${call.sessions.get<JwtCookie>()}")
                val failureString = call.authentication.allFailures.joinToString { "\t${it.javaClass}" }
                val errorString = call.authentication.allErrors.joinToString("\n") { "\t${it.message}" }
                call.respond(HttpStatusCode.Unauthorized, "Errors:\n$errorString\n\nFailures:\n$failureString")
            }

            validate { session: JwtCookie ->
                println("Validating the session auth \n\n$session\n")
                session
            }
        }
    }

    install(Routing) {
        dev()
        user()
        healthcheck()
        routes()
        coordinates()
    }
}
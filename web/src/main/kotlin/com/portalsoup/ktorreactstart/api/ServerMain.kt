package com.portalsoup.ktorreactstart.api

import com.portalsoup.ktorreactstart.api.routes.AuthApi.user
import com.portalsoup.ktorreactstart.api.routes.HealthcheckApi.healthcheck
import com.portalsoup.ktorexposed.core.service.UserService
import com.portalsoup.ktorexposed.core.util.JwtCookie
import io.ktor.application.*
import io.ktor.auth.Authentication
import io.ktor.auth.authentication
import io.ktor.auth.session
import io.ktor.features.CORS
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.*
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie
import io.ktor.sessions.get
import io.ktor.sessions.sessions

fun Application.main() {
    // Setup
    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Get)
        method(HttpMethod.Post)

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
        header(HttpHeaders.ContentLength)
        header(HttpHeaders.ContentType)


        host("localhost")
        host("localhost:8080")

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
                UserService.currentUser(session)
            }
        }
    }

    install(Routing) {
        // Configure client
        resources("static")
        defaultResource("static/index.html")

        user()
        healthcheck()
    }
}

suspend fun ApplicationCall.respondDefaultHtml() {

}
package com.portalsoup.ktorexposed.api.routes

import com.portalsoup.ktorexposed.core.JwtConfig
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.sessions.sessions

fun Route.healthcheck() {
    get("/healthcheck") {
        call.respondText("POWER OVERWHELMING", ContentType.Text.Plain)
    }
}

fun Route.authedHealthcheck() {
    authenticate {
        get("authed/healthcheck") {
            val jwt = call.sessions.get("jwt") as String

            call.respondText("So logged in!", ContentType.Text.Plain)
        }
    }
}
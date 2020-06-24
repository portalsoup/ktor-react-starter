package com.portalsoup.ktorexposed.api.routes

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing

fun Application.healthcheckRoutes() {
    routing {
        get("/healthcheck") {
            call.respondText("POWER OVERWHELMING", ContentType.Text.Plain)
        }

        authenticate {
            get("authed/healthcheck") {
                call.respondText("So logged in!", ContentType.Text.Plain)
            }
        }
    }
}
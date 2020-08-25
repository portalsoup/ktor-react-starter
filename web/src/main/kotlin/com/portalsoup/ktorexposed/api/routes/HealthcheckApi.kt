package com.portalsoup.ktorexposed.api.routes

import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.sessions.sessions

fun Route.healthcheck() {
    get("/healthcheck") {
        call.respondText("POWER OVERWHELMING", ContentType.Text.Plain)
    }
}
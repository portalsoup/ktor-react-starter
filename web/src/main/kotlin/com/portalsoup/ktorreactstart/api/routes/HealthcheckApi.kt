package com.portalsoup.ktorreactstart.api.routes

import com.portalsoup.ktorreactstart.api.BaseApi
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.sessions.sessions

object HealthcheckApi : BaseApi {


    fun Route.healthcheck() {
        get("/healthcheck") {
            call.respondText("POWER OVERWHELMING", ContentType.Text.Plain)
        }
    }
}
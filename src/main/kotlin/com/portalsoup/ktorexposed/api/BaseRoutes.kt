package com.portalsoup.ktorexposed.api

import com.portalsoup.ktorexposed.api.routes.healthcheckRoutes
import com.portalsoup.ktorexposed.api.routes.authRoutes
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.routing.routing

fun Application.main() {
    install(CallLogging)
    install(ContentNegotiation) { gson { } }

    routing {
        application.healthcheckRoutes()
        application.authRoutes()
    }
}


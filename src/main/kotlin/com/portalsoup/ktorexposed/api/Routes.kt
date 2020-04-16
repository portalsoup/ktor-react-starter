package com.portalsoup.ktorexposed.api

import com.portalsoup.ktorexposed.service.fullName
import com.portalsoup.ktorexposed.service.listNames
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import java.lang.RuntimeException


fun Application.main() {
    routing {
        // Status
        get("/status/healthcheck") {
            call.respondText("Yes, I live!", ContentType.Text.Plain)
        }


        // User
        get("/user/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw RuntimeException("No id was provided to GET /user/:id")
            call.respondText("Hello, ${fullName(id)}!", ContentType.Text.Plain)
        }

        get("/user/all") {
            call.respondText(listNames().joinToString("\n"), ContentType.Text.Plain)
        }
    }
}
package com.portalsoup.ktorexposed.api.routes

import com.portalsoup.ktorexposed.api.resources.RouteResource
import com.portalsoup.ktorexposed.service.CoordinateService
import com.portalsoup.ktorexposed.service.RouteService
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import java.lang.RuntimeException

fun Route.routes() {
    route("/route") {
        get("/{id}") {
            val id = call.parameters["id"]?.toInt()
                ?: throw RuntimeException("No valid ID found.")

            RouteService[id]
        }

        post("/") {
            val newRoutes = call.receive<List<RouteResource>>()
            val newIds = RouteService.create(newRoutes)
            call.respond(newIds)
        }

        route("/import") {
            post("/gpx") {
                // TODO
            }
        }
    }
}
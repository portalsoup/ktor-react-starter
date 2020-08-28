package com.portalsoup.ktorexposed.api.routes

import com.portalsoup.ktorexposed.core.service.RouteService
import com.portalsoup.ktorexposed.resources.RouteResource
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*

fun Route.routes() {
    route("/route") {
        get("/{id}") {
            val id = call.parameters["id"]?.toInt()
                ?: throw RuntimeException("No valid ID found.")
            val route = RouteService.getRoute(id) ?: throw RuntimeException("Route not found")
            call.respond(route)
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
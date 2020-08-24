package com.portalsoup.ktorexposed.api.routes

import com.portalsoup.ktorexposed.dao.RouteDAO
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

            RouteDAO[id]
        }

        post("/") {
            val newRoutes = call.receive<List<RouteResource>>()
            val newIds = RouteDAO.create(newRoutes)
            call.respond(newIds)
        }

        route("/import") {
            post("/gpx") {
                // TODO
            }
        }
    }
}
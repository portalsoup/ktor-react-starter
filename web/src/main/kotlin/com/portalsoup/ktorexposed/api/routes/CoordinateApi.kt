package com.portalsoup.ktorexposed.api.routes

import com.portalsoup.ktorexposed.api.BaseApi
import com.portalsoup.ktorexposed.core.service.CoordinateService
import com.portalsoup.ktorexposed.resources.CoordinateResource
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import org.jetbrains.exposed.sql.transactions.transaction

object CoordinateApi : BaseApi {

    fun Route.coordinates() {
        route("/coordinate") {
            get("/{id}") {
                val id = call.parameters["id"]?.toInt()
                    ?: throw RuntimeException("No valid ID found.")

                val coordinate =
                    transaction { CoordinateService.getCoordinate(id) }
                        ?: throw RuntimeException("failed to find coordinate")

                call.respond(coordinate)
            }

            post("/") {
                val newCoordinate = call.receive<List<CoordinateResource>>()
                val newIds = CoordinateService.create(newCoordinate)
                call.respond(newIds)
            }
        }
    }
}
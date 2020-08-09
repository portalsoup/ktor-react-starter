package com.portalsoup.ktorexposed.api.routes

import com.portalsoup.ktorexposed.api.resources.CoordinateResource
import com.portalsoup.ktorexposed.api.resources.EntityCreatedResource
import com.portalsoup.ktorexposed.api.resources.persist
import com.portalsoup.ktorexposed.entity.Coordinates
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.RuntimeException

fun Route.coordinates() {
    route("/coordinate") {
        get("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw RuntimeException("No valid ID found.")
            transaction {
                Coordinates
                    .select { Coordinates.id eq id }
                    .single()
            }
        }

        post("/") {
            val newCoordinate = call.receive<CoordinateResource>()

            val persisted: EntityID<Int> = transaction {
                newCoordinate.persist()
            }

            call.respond(EntityCreatedResource(persisted.value))
        }
    }
}
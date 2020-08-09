package com.portalsoup.ktorexposed.api.routes

import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route

fun Route.routes() {
    route("/route") {
        get("/{id}") {

        }

        post("/") {

        }

        route("/import") {
            post("/gpx") {

            }
        }
    }
}
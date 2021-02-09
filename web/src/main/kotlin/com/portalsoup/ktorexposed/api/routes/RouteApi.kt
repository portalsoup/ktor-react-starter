package com.portalsoup.ktorexposed.api.routes

import com.portalsoup.ktorexposed.api.BaseApi
import com.portalsoup.ktorexposed.core.monad.Try
import com.portalsoup.ktorexposed.core.monad.Try.Failure
import com.portalsoup.ktorexposed.core.monad.Try.Success
import com.portalsoup.ktorexposed.core.service.GPXService
import com.portalsoup.ktorexposed.core.service.RouteService
import com.portalsoup.ktorexposed.resources.RouteResource
import com.portalsoup.ktorexposed.resources.toResource
import io.jenetics.jpx.GPX
import io.ktor.application.call
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.respond
import io.ktor.routing.*
import java.io.File

object RouteApi : BaseApi {

    fun Route.routes() {
        route("/route") {
            get("/{id}") {
                val id = call.parameters["id"]?.toInt()
                    ?: throw RuntimeException("No valid ID found.")
                val route = RouteService.getRoute(id) ?: throw RuntimeException("Route not found")
                println("got the route $route")
                call.respond(route)
            }

            post("/") {
                val newRoutes = call.receive<List<RouteResource>>()
                val newIds = RouteService.create(newRoutes)
                call.respond(newIds)
            }

            authenticate {
                route("/import") {

                    post("/gpx") {
                        when (val maybeUser = withIdentity(call) { it }) {
                            is Success -> call.receiveMultipart()
                                .forEachPart {
                                    when (it) {
                                        is PartData.FileItem -> {
                                            val gpx = GPXService.parseGpxInputStream(it.streamProvider())
                                            GPXService.importGpx(gpx, maybeUser.data.toResource())
                                            call.respond(HttpStatusCode.OK)
                                        }
                                        else -> call.respond(HttpStatusCode.BadRequest)
                                    }
                                }
                            is Failure -> call.respond(HttpStatusCode.NotFound)
                        }
                    }

                }
            }
        }
    }
}
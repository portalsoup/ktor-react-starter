package com.portalsoup.ktorexposed.api.routes

import com.portalsoup.ktorexposed.api.BaseApi
import com.portalsoup.ktorexposed.core.service.GPXService
import com.portalsoup.ktorexposed.core.service.RouteService
import com.portalsoup.ktorexposed.core.util.getLogger
import com.portalsoup.ktorexposed.resources.RouteResource
import io.jenetics.jpx.GPX
import io.ktor.application.call
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.respond
import io.ktor.routing.*
import java.io.File

object RouteApi : BaseApi {

    val log = getLogger(javaClass)

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

            route("/import") {
                post("/gpx") {
                    println("Handling import")
                    call.receiveMultipart()
                        .forEachPart {
                            println("in a part $it")
                            when (it) {
                                is PartData.FileItem -> {
                                    println("it is a file")
                                    val gpx = GPXService.parseGpxInputStream(it.streamProvider())
                                    println("Got the gpx $gpx")
                                    GPXService.importGpx(gpx)
                                    call.respond(HttpStatusCode.OK)
                                }
                                is PartData.FormItem -> {
                                    println("Found it??\n\n%{it.value}\n\n")
                                }
                            }
                        }
                }
            }
        }
    }
}
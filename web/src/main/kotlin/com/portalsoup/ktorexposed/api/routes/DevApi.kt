package com.portalsoup.ktorexposed.api.routes

import com.portalsoup.ktorexposed.core.service.DevService
import io.ktor.routing.*
import java.io.InputStream

class GpxReader() {
    fun read(): InputStream = javaClass.getResourceAsStream("sample-route-strava.gpx")
}

fun Route.dev() {

    post("load-data") {
       DevService.loadData()
    }
}
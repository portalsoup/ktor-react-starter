package com.portalsoup.ktorexposed.api.routes

import com.portalsoup.ktorexposed.api.BaseApi
import com.portalsoup.ktorexposed.core.service.DevService
import io.ktor.routing.*
import java.io.InputStream

class GpxReader() : BaseApi {
    fun read(): InputStream = javaClass.getResourceAsStream("sample-route-strava.gpx")
}

object DevApi {

    fun Route.dev() {

        post("load-data") {
            DevService.loadData()
        }
    }
}
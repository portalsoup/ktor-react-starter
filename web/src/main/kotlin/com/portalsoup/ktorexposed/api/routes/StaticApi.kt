package com.portalsoup.ktorexposed.api.routes

import com.portalsoup.ktorexposed.api.BaseApi
import com.sun.org.apache.bcel.internal.util.ClassPath
import io.ktor.http.content.*
import io.ktor.routing.*

object StaticApi : BaseApi {

    fun Route.assets() {
        static {
            resource("/", "index.html")
            resource("*", "index.html")

            static("assets") {
                resources("assets")
            }
        }
    }
}
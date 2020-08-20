package com.portalsoup.ktorexposed.api.routes

import com.portalsoup.ktorexposed.api.resources.TravelerAuthResource
import com.portalsoup.ktorexposed.core.JwtCookie
import com.portalsoup.ktorexposed.service.UserService
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.auth.authentication
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.sessions.sessions
import io.ktor.sessions.set
import java.lang.RuntimeException


fun Route.user() {
    route("") {
        post("sign-up") {
            val signupResource: TravelerAuthResource = call.receive()
            val newUserResource = UserService.signup(signupResource)
            call.respond(newUserResource)
        }

        post("sign-in") {
            val credentials: TravelerAuthResource = call.receive()
            call.sessions.set(UserService.signin(credentials))
            call.respond(HttpStatusCode.OK)
        }

        authenticate {
            get("currentUser") {
                val principal = call.authentication.principal<JwtCookie>() ?: throw RuntimeException("null principal")
                val currentUser = UserService.currentUser(principal)
                call.respond(currentUser)
            }
        }
    }
}
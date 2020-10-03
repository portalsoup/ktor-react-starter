package com.portalsoup.ktorexposed.api.routes

import com.portalsoup.ktorexposed.api.BaseApi
import com.portalsoup.ktorexposed.core.util.JwtCookie
import com.portalsoup.ktorexposed.resources.TravelerResource
import com.portalsoup.ktorexposed.core.service.UserService
import com.portalsoup.ktorexposed.core.util.getLogger
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
import org.jetbrains.exposed.sql.transactions.transaction

object AuthApi : BaseApi {

    val log = getLogger(javaClass)

    fun Route.user() {
        route("") {
            post("sign-up") {
                val signupResource: TravelerResource = call.receive()
                val newUserResource = UserService.signup(signupResource)
                call.respond(newUserResource)
            }

            post("sign-in") {
                val credentials: TravelerResource = call.receive()
                val jwt = UserService.signin(credentials)
                call.sessions.set(jwt)
                call.respond(HttpStatusCode.OK)
            }

            authenticate {
                get("currentUser") {
                    val principal =
                        call.authentication.principal<JwtCookie>() ?: throw RuntimeException("null principal")
                    val currentUser = UserService.currentUser(principal)
                    call.respond(currentUser)
                }
            }
        }
    }
}
package com.portalsoup.ktorreactstart.api.routes

import com.portalsoup.ktorexposed.core.data.dao.UserDAO
import com.portalsoup.ktorexposed.core.data.entity.toResource
import com.portalsoup.ktorreactstart.api.BaseApi
import com.portalsoup.ktorexposed.core.util.Try
import com.portalsoup.ktorexposed.core.util.Try.Failure
import com.portalsoup.ktorexposed.core.util.Try.Success
import com.portalsoup.ktorexposed.core.resources.UserResource
import com.portalsoup.ktorexposed.core.service.UserService
import com.portalsoup.ktorexposed.core.util.JwtCookie
import com.portalsoup.ktorexposed.core.resources.CurrentUserResource
import com.portalsoup.ktorexposed.core.util.log
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.sessions.sessions
import io.ktor.sessions.set

object AuthApi : BaseApi {

    fun Route.user() {
        route("") {
            post("sign-up") {
                val signupResource: UserResource = call.receive()

                when (val maybeNewUser = UserService.signup(signupResource)) {
                    is Success -> {
                        log().info("New user: ${maybeNewUser.data}")
                        call.respond(maybeNewUser.data)
                    }
                    is Failure -> {
                        log().warn("Failed to create use ${maybeNewUser.error.message}")
                        call.respond(HttpStatusCode.BadRequest)
                    }
                }
            }

            post("sign-in") {
                val credentials: UserResource = call.receive()
                val jwt = UserService.generateAuthCookie(credentials)
                call.sessions.set(jwt)
                (credentials.id ?: UserService.currentUser(jwt).id)
                    .let { UserService.incrementLoggedInCount(it) }
                call.respond(HttpStatusCode.OK)
            }

            authenticate {
                post("sign-out") {
                    val cookieName = call.sessions.findName(JwtCookie::class)
                    when (withIdentity(call) { it }) {
                        is Success -> call.sessions.clear(cookieName)
                        is Failure -> call.respond(HttpStatusCode.BadRequest)
                    }
                }

                get("currentUser") {
                    when (val maybeUser: Try<CurrentUserResource> = withIdentity(call) { it }) {
                        is Success -> call.respond(maybeUser.data)
                        is Failure -> call.respond(HttpStatusCode.BadRequest)
                    }
                }
            }
        }
    }
}
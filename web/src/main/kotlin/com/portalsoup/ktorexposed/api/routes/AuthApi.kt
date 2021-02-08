package com.portalsoup.ktorexposed.api.routes

import com.portalsoup.ktorexposed.api.BaseApi
import com.portalsoup.ktorexposed.core.monad.Try
import com.portalsoup.ktorexposed.core.monad.Try.Failure
import com.portalsoup.ktorexposed.core.monad.Try.Success
import com.portalsoup.ktorexposed.resources.TravelerResource
import com.portalsoup.ktorexposed.core.service.UserService
import com.portalsoup.ktorexposed.resources.CurrentUserResource
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
                    val maybeUser: Try<CurrentUserResource> = withIdentity(call) { it }
                    when (maybeUser) {
                        is Success -> call.respond(maybeUser.data)
                        is Failure -> call.respond(HttpStatusCode.BadRequest)
                    }
                }
            }
        }
    }
}
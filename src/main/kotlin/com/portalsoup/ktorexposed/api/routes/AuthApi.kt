package com.portalsoup.ktorexposed.api.routes

import com.portalsoup.ktorexposed.Config
import com.portalsoup.ktorexposed.api.MySession
import com.portalsoup.ktorexposed.api.resources.EntityCreatedResource
import com.portalsoup.ktorexposed.api.resources.TravelerAuthResource
import com.portalsoup.ktorexposed.core.JwtUtils
import com.portalsoup.ktorexposed.core.SecurePassword
import com.portalsoup.ktorexposed.entity.Traveler
import com.portalsoup.ktorexposed.service.checkAuth
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.auth.authentication
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.sessions.sessions
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.RuntimeException


fun Route.user() {
    route("") {
        post("sign-up") {
            val signupResource: TravelerAuthResource = call.receive()
            val securePassword = SecurePassword(signupResource.password)
            val newId: Int = transaction {
                val inserted = Traveler.insert {
                    it[email] = signupResource.email
                    it[passwordHash] = securePassword.hashPassword()
                    it[passwordSalt] = securePassword.userSalt
                }
                inserted get Traveler.id
            }.value
            call.respond(EntityCreatedResource(newId))
        }

        post("sign-in") {
            val credentials: TravelerAuthResource = call.receive()
            val user = transaction { checkAuth(credentials) }
            val token = JwtUtils.makeToken(user)
            call.sessions.set("SESSION", MySession(user.id.value, token))
            call.respond(HttpStatusCode.Accepted)
        }

        authenticate("user") {
            get("currentUser") {
                println("Entered currentUser method")
                val principal = call.authentication.principal<JWTPrincipal>() ?: throw RuntimeException("null principal")
                println("principal $principal")
                call.respondText { principal.payload.getClaim("email").asString() }
            }
        }
    }
}
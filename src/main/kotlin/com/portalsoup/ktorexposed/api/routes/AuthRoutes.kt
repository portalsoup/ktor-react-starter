package com.portalsoup.ktorexposed.api.routes

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.portalsoup.ktorexposed.api.resources.SignupResource
import com.portalsoup.ktorexposed.core.JwtConfig
import com.portalsoup.ktorexposed.core.SecurePassword
import com.portalsoup.ktorexposed.entity.Traveler
import com.portalsoup.ktorexposed.service.checkAuth
import com.portalsoup.ktorexposed.service.toHexString
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.UserPasswordCredential
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.auth.jwt.jwt
import io.ktor.client.request.request
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.post
import io.ktor.routing.routing
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.RuntimeException


fun Route.signup() {
    post("signup") {

        println("entered closure")
        val signupResource = call.receive<SignupResource>()
        val securePassword = SecurePassword(signupResource.password)
        println("\npassword = [${signupResource.password}]\n")
        println("hash = ${securePassword.hashPassword()}")
        println("salt? ${securePassword.userSalt} ${securePassword.userSalt}")

        transaction {
            val inserted = Traveler.insert {
                it[email] = signupResource.email
                it[passwordHash] = securePassword.hashPassword()
                it[passwordSalt] = securePassword.userSalt
            }
            println(inserted)
        }
        transaction {
            Traveler
                .select {Traveler.email eq signupResource.email}
                .single()
        }
        call.respondText("Done", ContentType.Text.Plain, HttpStatusCode.OK)
    }
}

fun Route.login() {
    post("login") {
        println("first")
        val credentials: UserPasswordCredential = call.receive()
        println("credentials:  ${credentials.name} [${credentials.password}]")

        val user = transaction { checkAuth(credentials) } ?: throw RuntimeException("No user")
        println("second $user")

        if (user != null) {
            val token = JwtConfig.makeToken(user)
            call.respond(token)
        } else {
            call.respondText("failed.", ContentType.Text.Plain, HttpStatusCode.Forbidden)
        }
    }
}
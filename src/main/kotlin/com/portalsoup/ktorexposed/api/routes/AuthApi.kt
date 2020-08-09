package com.portalsoup.ktorexposed.api.routes

import com.portalsoup.ktorexposed.api.resources.TravelerAuthResource
import com.portalsoup.ktorexposed.core.JwtConfig
import com.portalsoup.ktorexposed.core.SecurePassword
import com.portalsoup.ktorexposed.entity.Traveler
import com.portalsoup.ktorexposed.service.checkAuth
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.post
import io.ktor.sessions.sessions
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.RuntimeException


fun Route.signup() {
    post("sign-up") {

        println("entered closure")
        val signupResource: TravelerAuthResource = call.receive()
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
    post("sign-in") {
        println("first")
        val credentials: TravelerAuthResource = call.receive()
        println("credentials:  ${credentials.email} [${credentials.password}]")

        val user = transaction { checkAuth(credentials) } ?: throw RuntimeException("No user")
        println("second $user")

        if (user != null) {
            val token = JwtConfig.makeToken(user)
            call.sessions.set("jwt", token)
        } else {
            call.respondText("failed.", ContentType.Text.Plain, HttpStatusCode.Forbidden)
        }
    }
}
package com.portalsoup.ktorexposed

import com.portalsoup.ktorexposed.entity.User
import com.portalsoup.ktorexposed.service.fullName
import com.portalsoup.ktorexposed.service.printAllNames
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.jetty.Jetty
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.RuntimeException

fun main(args: Array<String>) {
    DatabaseFactory.init()

    // This just creates duplicated data every launch.  Doesn't matter this early
    transaction {
        println("Creating some dummy data")
        User.insert {
            it[firstName] = "Poopyface"
            it[lastName] = "Sr."
        }
        User.insert {
            it[firstName] = "Poopyface"
            it[lastName] = "Jr."
        }
    }

    printAllNames()

    embeddedServer(Jetty, 8080) {
        routing {
            get("/user/{id}") {
                val id = call.parameters["id"]?.toInt() ?: throw RuntimeException("No id was provided to GET /user/:id")
                call.respondText("Hello, ${fullName(id)}!", ContentType.Text.Html)
            }
        }
    }.start(wait = true)

}



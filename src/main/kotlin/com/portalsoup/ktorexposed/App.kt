package com.portalsoup.ktorexposed

import com.portalsoup.ktorexposed.entity.User
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.jetty.Jetty
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

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

    embeddedServer(Jetty, commandLineEnvironment(args)).start(wait = true)

}



package com.portalsoup.ktorexposed

import com.portalsoup.ktorexposed.entity.User
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.jetty.Jetty
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun main(args: Array<String>) {
    DatabaseFactory.init()

    embeddedServer(Jetty, commandLineEnvironment(args)).start(wait = true)

}



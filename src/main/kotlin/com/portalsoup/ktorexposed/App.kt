package com.portalsoup.ktorexposed

import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.jetty.Jetty

fun main(args: Array<String>) {
    DatabaseFactory.init()

    embeddedServer(Jetty, commandLineEnvironment(args)).start(wait = true)
}


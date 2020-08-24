package com.portalsoup.ktorexposed

import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main(args: Array<String>) {
    DatabaseFactory.init()

    embeddedServer(Netty, commandLineEnvironment(args)).start(wait = true)
}
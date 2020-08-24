package com.portalsoup.ktorexposed

import com.google.gson.Gson
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import java.io.File
import java.lang.RuntimeException
import java.net.URL

fun main(args: Array<String>) {
    DatabaseFactory.init()

    embeddedServer(Netty, commandLineEnvironment(args)).start(wait = true)
}
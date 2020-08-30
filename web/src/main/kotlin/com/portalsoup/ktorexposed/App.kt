package com.portalsoup.ktorexposed

import com.google.gson.Gson
import com.portalsoup.ktorexposed.api.main
import com.portalsoup.ktorexposed.core.util.Retrier
import com.portalsoup.ktorexposed.resource.NewTravelerResource
import io.ktor.server.engine.*
import io.ktor.server.netty.Netty
import java.io.BufferedReader
import java.lang.RuntimeException

fun main(args: Array<String>) {

    // TODO This go on coroutine?
    Retrier("database initialization") {
        DatabaseFactory.init()
    }
    embeddedServer(Netty, commandLineEnvironment(args)).start(wait = true)
}
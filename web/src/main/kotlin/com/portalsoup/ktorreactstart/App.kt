package com.portalsoup.ktorreactstart

import com.portalsoup.ktorexposed.core.util.Retrier
import io.ktor.server.engine.*
import io.ktor.server.netty.Netty

fun main(args: Array<String>) {

    // TODO This go on coroutine?
    Retrier("database initialization") {
        DatabaseFactory.init()
    }
    embeddedServer(Netty, commandLineEnvironment(args)).start(wait = true)
}



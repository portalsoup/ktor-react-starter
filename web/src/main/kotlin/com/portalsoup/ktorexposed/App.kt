package com.portalsoup.ktorexposed

import com.portalsoup.ktorexposed.api.main
import com.portalsoup.ktorexposed.core.util.Retrier
import io.ktor.server.engine.*
import io.ktor.server.netty.Netty

fun main(args: Array<String>) {

    Retrier(
        "database initialization"
    ) {
        DatabaseFactory.init()
    }

    val env = applicationEngineEnvironment {
        module {
            this.main()
        }

        // admin api
        connector {
            host = Config.global.server.adminIP
            port = Config.global.server.adminPort
        }

        // public api
        connector {
            host = Config.global.server.publicIP
            port = Config.global.server.publicPort
        }
    }

    embeddedServer(Netty, env).start(wait = true)
}
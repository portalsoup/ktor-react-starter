package com.portalsoup.ktorexposed.core.service

import com.portalsoup.ktorexposed.Config
import com.portalsoup.ktorexposed.core.monad.Try
import com.portalsoup.ktorexposed.core.monad.Try.Failure
import com.portalsoup.ktorexposed.core.monad.Try.Success
import io.ktor.application.*
import java.lang.RuntimeException


object AdminService {

    fun <T> requireAdminApi(call: ApplicationCall, lambda: (call: ApplicationCall) -> T): Try<T> {
        val port = call.request.local.port
        if (port != Config.global.server.adminPort) {
            return Failure(RuntimeException("Incorrect port: [$port]"))
        }

        val result: Result<T> = kotlin.runCatching {
            lambda(call)
        }

        return try {
            Success(result.getOrThrow())
        } catch (t: Throwable) {
            Failure(t)
        }
    }
}


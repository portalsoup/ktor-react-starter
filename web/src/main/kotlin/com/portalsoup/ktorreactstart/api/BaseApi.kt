package com.portalsoup.ktorreactstart.api

import com.portalsoup.ktorexposed.core.util.Try
import com.portalsoup.ktorexposed.core.resources.CurrentUserResource
import com.portalsoup.ktorexposed.core.util.Logging
import io.ktor.application.ApplicationCall
import io.ktor.auth.authentication

interface BaseApi: Logging {

    fun <T> withIdentity(call: ApplicationCall, lambda: (CurrentUserResource) -> T?): Try<T> =
        call.authentication.principal<CurrentUserResource>()
            ?.let(lambda)
            ?.let { Try.Success(it) }
            ?: Try.Failure(RuntimeException("Null Principal"))
}
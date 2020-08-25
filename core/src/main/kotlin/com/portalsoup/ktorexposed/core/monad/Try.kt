package com.portalsoup.ktorexposed.core.monad

import java.lang.NullPointerException

sealed class Try<out T>(val _data: T?, val _error: Throwable?) {
    data class Success<out T>(val data: T): Try<T>(data, null)
    data class Failure(val error: Throwable): Try<Nothing>(null, error)

    fun <R> map(transform: (T) -> R): Try<R> {
        return when (this) {
            is Success -> try {
                Success(transform(data))
            } catch (t: Throwable) {
                Failure(t)
            }
            is Failure -> Failure(error)
        }
    }

    fun <T> getOrThrow(wrapper: Throwable? = null): T {
        throwOnFailure(wrapper)
        return if (_data != null) {
            _data as T
        } else throw wrapException(wrapper, NullPointerException())
    }

    fun throwOnFailure(wrapper: Throwable? = null) {
        if (this is Failure) {
            throw wrapException(wrapper, error)
        }
    }

    private fun wrapException(wrapper: Throwable?, e: Throwable): Throwable {
        wrapper?.addSuppressed(e)
        return wrapper ?: e
    }

}
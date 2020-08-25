package com.portalsoup.ktorexposed.core.monad

import java.lang.NullPointerException

/**
 * Usage example:
 *
 *      val maybeSomething: Try<String> = ...
 *
 *      val str: String = when (maybeSomething) {
 *          is Success -> x.data
 *          is Failure -> throw x.error
 */
sealed class Try<out T> {
    data class Success<out T>(val data: T): Try<T>()
    data class Failure(val error: Throwable): Try<Nothing>()

    fun isSuccess(): Boolean = when (this) {
        is Success -> true
        is Failure -> false
    }

    fun isFailure(): Boolean = when (this) {
        is Failure -> true
        is Success -> false
    }

    fun throwOnFailure(): Try<T> = when (this) {
        is Failure -> throw error
        is Success -> Success(data)
    }

    fun wrapException(wrapper: Throwable): Try<T> = when (this) {
        is Failure -> {
            wrapper.addSuppressed(error)
            Failure(wrapper)
        }
        is Success<T> -> Success(data)
    }

    fun <R> map(transform: (T) -> R): Try<R> = when (this) {
        is Success -> {
            try {
                Success(transform(data))
            } catch (t: Throwable) {
                Failure(t)
            }
        }
        is Failure -> Failure(error)
    }
}
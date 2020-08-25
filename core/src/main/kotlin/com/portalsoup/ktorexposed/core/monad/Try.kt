package com.portalsoup.ktorexposed.core.monad

import java.lang.NullPointerException

/**
 * A mechanism to wrap the concept of a success and failure state.
 *
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
    data class Failure(val error: Throwable, val status: Int = 404): Try<Nothing>()

    fun isSuccess(): Boolean = when (this) {
        is Success -> true
        is Failure -> false
    }

    fun isFailure(): Boolean = when (this) {
        is Failure -> true
        is Success -> false
    }

    fun throwOnFailure(): Unit = when (this) {
        is Failure -> throw error
        is Success -> Unit
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
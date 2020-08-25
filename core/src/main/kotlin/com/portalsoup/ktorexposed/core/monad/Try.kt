package com.portalsoup.ktorexposed.core.monad

import java.lang.NullPointerException

/**
 * A mechanism to wrap the concept of a success and failure state.
 *
 * Usage example:
 *
 *      // Example method declaration
 *      val maybeSomething: Try<String> = ...
 *
 *      val str: String = when (maybeSomething) {
 *          is Success> -> x.data
 *          is Failure -> x.error
 */
sealed class Try<out T>() {
    data class Success<out T>(val data: T): Try<T>()
    data class Failure(val error: Throwable): Try<Nothing>()

    fun isSuccess(): Boolean = when (this) {
        is Success<T> -> true
        else -> false
    }

    fun isFailure(): Boolean = when (this) {
        is Failure -> true
        else -> false
    }

    fun throwOnFailure() {
        if (this is Failure) throw error
    }

    fun wrapException(wrapper: Throwable): Try<T> {
        return when (this) {
            is Failure -> {
                wrapper.addSuppressed(error)
                Failure(wrapper)
            }
            is Success<T> -> Success(data)
        }
    }

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
}
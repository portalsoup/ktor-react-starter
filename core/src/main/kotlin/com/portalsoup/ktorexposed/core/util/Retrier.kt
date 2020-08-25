package com.portalsoup.ktorexposed.core.util

import java.lang.Exception
import java.lang.RuntimeException

data class RetryConfig(
    val times: Int = 5,
    val intervalSeconds: Int = 5,
    val backoffIncrementSeconds: Int = 5
)

object Retrier {
    operator fun <T> invoke(
        name: String,
        config: RetryConfig = RetryConfig(),
        lambda: () -> T
    ): T {
        for (x in 1..config.times) {
            try {
                return lambda()
            } catch (e: Exception) {
                println("Retrying $name failed with the exception: ${e.message}")
                Thread.sleep((config.intervalSeconds * 1000L) + (5 * x)) // interval + 5 seconds per try
            }
        }
        throw RetryException("$name failed ${config.times} times.")
    }
}

data class RetryException(val str: String, val e: Exception? = null) : RuntimeException(str, e)
package com.portalsoup.ktorexposed.core.util

import java.lang.Exception
import java.lang.RuntimeException

data class RetryConfig(
    val maxTries: Int = 5,
    val firstInterval: Int = 5,
    val nextBackoffInterval: (timesWaited: Int) -> Int = { it * 5 } // default adds 5 seconds per wait
)

object Retrier {
    operator fun <T> invoke(
        name: String,
        config: RetryConfig = RetryConfig(),
        lambda: () -> T
    ): T {
        for (x in 1..config.maxTries) {
            try {
                return lambda()
            } catch (e: Exception) {
                println("Retrying $name failed with the exception: ${e.message}")
                Thread.sleep((config.firstInterval * 1000L) + config.nextBackoffInterval(x)) // interval + 5 seconds per try
            }
        }
        throw RetryException("$name failed ${config.maxTries} times.")
    }
}

data class RetryException(val str: String, val e: Exception? = null) : RuntimeException(str, e)
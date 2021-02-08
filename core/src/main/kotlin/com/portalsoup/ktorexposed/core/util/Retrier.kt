package com.portalsoup.ktorexposed.core.util

import com.portalsoup.ktorexposed.utils.getLogger
import java.lang.Exception
import java.lang.RuntimeException

data class RetryConfig(
    val maxTries: Int = 5,
    val firstInterval: Int = 5,
    val nextBackoffInterval: (timesWaited: Int) -> Int = { it * 5 } // default adds 5 seconds per wait
)

object Retrier {
    private val log = getLogger(Retrier.javaClass)
    operator fun <T> invoke(
        name: String,
        config: RetryConfig = RetryConfig(),
        lambda: () -> T
    ): T {
        for (x in 1..config.maxTries) {
            log.info("Attempting to run ${name}...")
            try {
                val success = lambda()
                log.info("Success!")
                return success
            } catch (e: Exception) {
                println("Failed because: : [${e.message}] with ${config.maxTries - x} tries remaining.  Waiting for ${config.nextBackoffInterval(x)} seconds")
                Thread.sleep((config.firstInterval * 1000L) + config.nextBackoffInterval(x)) // interval + 5 seconds per try
            }
        }
        throw RetryException("$name failed ${config.maxTries} times.")
    }
}

data class RetryException(val str: String, val e: Exception? = null) : RuntimeException(str, e)
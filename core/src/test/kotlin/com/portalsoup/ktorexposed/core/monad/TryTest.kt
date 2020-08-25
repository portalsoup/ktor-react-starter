package com.portalsoup.ktorexposed.core.monad

import com.natpryce.hamkrest.Matcher
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.hasSize
import com.portalsoup.ktorexposed.core.monad.Try.Failure
import com.portalsoup.ktorexposed.core.monad.Try.Success
import com.portalsoup.ktorexposed.core.test.ExpectedException
import com.portalsoup.ktorexposed.core.test.Matchers.isTryFailure
import com.portalsoup.ktorexposed.core.test.Matchers.isTrySuccess
import org.testng.Assert.fail
import org.testng.annotations.Test

class TryTest {

    @Test
    fun successfulValueInWhenTest() {
        val expectedValue = 1

        when (val v: Try<Int> = Success(expectedValue)) {
            is Success<Int> -> {
                assertThat("Expected: $expectedValue Found: ${v.data}", v.data, equalTo(expectedValue))
            }
            else -> {
                fail("The Try was not the correct success type.  $v")
            }
        }
    }

    @Test
    fun isSuccessMethodTest() {
        val expectedValue = 1
        val v: Try<Int> = Success(expectedValue)

        assertThat(v, isTrySuccess)
    }

    @Test
    fun failureValueInWhenTest() {
        val expectedValue: Throwable = RuntimeException("Expected failure")

        when (val v: Try<Int> = Failure(expectedValue)) {
            is Failure -> {
                assertThat("Expected: $expectedValue Found: ${v.error}", v.error, equalTo(expectedValue))
            }
            else -> {
                fail("The Try was not the correct failure type.  $v")
            }
        }
    }

    @Test
    fun isFailureMethodTest() {
        val expectedValue: Throwable = RuntimeException("Expected failure")
        val v: Try<Int> = Failure(expectedValue)

        assertThat(v, isTryFailure)
    }

    @Test(expectedExceptions = [ExpectedException::class])
    fun throwOnFailureFailureTest() {
        val expectedValue: Throwable = ExpectedException()
        Failure(expectedValue).throwOnFailure()
    }

    @Test
    fun throwOnFailureSuccessTest() {
        val expectedValue = 1
        Success(expectedValue).throwOnFailure()
    }


    @Test
    fun wrapExceptionTest() {
        val unwrapped: Throwable = RuntimeException("Expected failure")
        val wrapped: Throwable = java.lang.RuntimeException("Wrapping it up")
        val v: Try<Int> = Failure(unwrapped)
            .wrapException(wrapped)

        when (v) {
            is Failure -> {
                assertThat(v.error.message, equalTo(wrapped.message))
                assertThat(v.error.suppressed.size, equalTo(1))
                assertThat(v.error.suppressed[0].message, equalTo(unwrapped.message))
            }
            is Success -> {
                fail("The try is in an unexpected success state")
            }
        }
    }

    @Test
    fun mapSameTypeTest() {
        val data = 5
        val multiplyBy = 2
        val v: Try<Int> = Success(data)
        val finalV: Try<Int> = v.map {
            it * multiplyBy
        }

        when (finalV) {
            is Success -> assertThat(finalV.data, equalTo(data * multiplyBy))
            is Failure -> fail("Try shouldn't of been Failure")
        }
    }

    @Test
    fun mapIntToStringTest() {
        val data = 5
        val v: Try<Int> = Success(data)
        val finalV: Try<String> = v.map {
            "$it"
        }

        when (finalV) {
            is Success -> assertThat(finalV.data, equalTo("$data"))
            is Failure -> fail("Try shouldn't of been Failure")
        }
    }

    @Test
    fun functorIdentityLawTest() {
        val u: Try<Int> = Success(1)
        val u2 = u.map { x: Int -> x }

        assertThat(u, equalTo(u2))
    }

    @Test
    fun functorCompositionLawTest() {
        val u: Try<Int> = Success(100)
        val f: (i: Int) -> Int = { it / 3 }
        val g: (i: Int) -> Int = { it / 2 }
        val nested = u.map { f(g(it)) }
        val chained = u
            .map { g(it) }
            .map { f(it) }

        assertThat(chained, equalTo(nested))
    }
}
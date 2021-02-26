package com.portalsoup.ktorexposed.core.test

import com.natpryce.hamkrest.Matcher
import com.portalsoup.ktorexposed.core.util.Try

object Matchers {
        val isTrySuccess = Matcher(Try<Int>::isSuccess)
        val isTryFailure = Matcher(Try<Int>::isFailure)
}
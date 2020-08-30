package com.portalsoup.ktorexposed.core.test

data class ExpectedException(val msg: String = "This is an expected error.", val error: Throwable? = null): AssertionError(msg, error)
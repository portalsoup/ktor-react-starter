package com.portalsoup.ktorexposed.core.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.full.companionObject

interface Logging

fun getLogger(forClass: Class<*>): Logger = LoggerFactory.getLogger(forClass)

inline fun <reified T: Logging> T.log(): Logger = getLogger(getClassForLogging(T::class.java))

fun <T: Any> getClassForLogging(javaClass: Class<T>): Class<*> =
    javaClass.enclosingClass?.takeIf {
        it.kotlin.companionObject?.java == javaClass
    } ?: javaClass
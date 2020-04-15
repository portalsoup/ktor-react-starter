package com.portalsoup.ktortest.util

import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import org.hibernate.cfg.Configuration
import org.hibernate.cfg.Environment
import org.hibernate.service.ServiceRegistry
import org.reflections.Reflections
import java.util.*

private fun registerEntities(configuration: Configuration) {
    val reflections = Reflections("com.portalsoup.ktortest.example.entity")
    reflections.getSubTypesOf(DiscoverableEntity::class.java).stream()
        .peek { o: Class<*> -> println(o) }
        .forEach { annotatedClass: Class<*> ->
            configuration.addAnnotatedClass(
                annotatedClass
            )
        }
}

private val sessionFactorySingleton: SessionFactory = try {
    val configuration = Configuration()
    val settings = Properties()
    settings[Environment.DRIVER] = "org.h2.Driver"
    settings[Environment.URL] = "jdbc:h2:./database/app"
    settings[Environment.USER] = "app"
    settings[Environment.PASS] = ""
    settings[Environment.DIALECT] = "org.hibernate.dialect.H2Dialect"
    settings[Environment.SHOW_SQL] = "true"
    settings[Environment.CURRENT_SESSION_CONTEXT_CLASS] = "thread"
    settings[Environment.HBM2DDL_AUTO] = "update"
    configuration.properties = settings
    registerEntities(configuration)
    val serviceRegistry: ServiceRegistry = StandardServiceRegistryBuilder()
        .applySettings(configuration.properties).build()
    configuration.buildSessionFactory(serviceRegistry)
} catch (e: Exception) {
    throw RuntimeException("Failed to init session factory!", e)
}

fun <R> withSessionFactory(lambda: SessionFactory.() -> R): R = sessionFactorySingleton.let(lambda)

fun <T> withSession(lambda: (Session) -> T): T = withSessionFactory { openSession().use { session -> lambda(session) }}

fun <T> withTransaction(lambda: (Session) -> T): T = withSession {
    val transaction = it.beginTransaction()

    transaction.run { lambda(it) }

    try {
        lambda(it)
    } catch (e: Throwable) {
        if (transaction != null) {
            it.beginTransaction()
        }
        throw e
    }
}
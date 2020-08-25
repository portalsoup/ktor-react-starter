package com.portalsoup.ktorexposed

import com.portalsoup.ktorexposed.core.util.Logging
import com.portalsoup.ktorexposed.core.util.log
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.flywaydb.core.api.FlywayException
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object DatabaseFactory: Logging {

    fun init() {
        val config = Config.global
        log().info("Beginning initialization!")
        val flyway = Flyway.configure().dataSource(config.db.jdbcUrl, config.db.username, config.db.password).load()
        migrateFlyway(flyway)

        Database.connect(hikari(config))
        log().info("App initialization complete!")
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T = newSuspendedTransaction { block() }

    private fun hikari(appConfig: AppConfig): HikariDataSource {
        log().info("Initializating Hikari")
        val hikariConfig = HikariConfig()
        hikariConfig.driverClassName = appConfig.db.driverClassName
        hikariConfig.jdbcUrl = appConfig.db.jdbcUrl
        hikariConfig.maximumPoolSize = appConfig.db.maximumPoolSize
        hikariConfig.isAutoCommit = appConfig.db.isAutoCommit
        hikariConfig.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        hikariConfig.username = appConfig.db.username
        hikariConfig.password = appConfig.db.password
        hikariConfig.validate()
        return HikariDataSource(hikariConfig)
    }

    fun migrateFlyway(flyway: Flyway, runAgain: Boolean = true) {
        log().info("Performing data migrations...")
        try {

            when (val migrationCount = flyway.migrate()) {
                0 -> "No migrations ran"
                else -> "Ran $migrationCount migrations"
            }.also {
                log().info(it)
            }

            flyway.validate()
        } catch (e: FlywayException) {
            log().info("An error occurred during migrations", e)
            flyway.repair()
            if (runAgain) {
                migrateFlyway(flyway, false)
            } else {
                throw e
            }
        }
    }

}
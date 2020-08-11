package com.portalsoup.ktorexposed

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.flywaydb.core.api.FlywayException
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object DatabaseFactory {

    fun init(config: AppConfig) {
        val flyway = Flyway.configure().dataSource(config.db.jdbcUrl, config.db.username, config.db.password).load()
        migrateFlyway(flyway)

        Database.connect(hikari(config))
//        transaction {
//            create(Traveler)
//        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T = newSuspendedTransaction { block() }

    private fun hikari(appConfig: AppConfig): HikariDataSource {
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
        try {
            flyway.migrate()
            flyway.validate()
        } catch (e: FlywayException) {
            flyway.repair()
            if (runAgain) {
                migrateFlyway(flyway, false)
            } else {
                throw e
            }
        }
    }

}
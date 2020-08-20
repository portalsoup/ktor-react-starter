package com.portalsoup.ktorexposed.service

import com.portalsoup.ktorexposed.api.resources.CoordinateResource
import com.portalsoup.ktorexposed.api.resources.EntityCreatedResource
import com.portalsoup.ktorexposed.api.resources.RouteResource
import com.portalsoup.ktorexposed.entity.*
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

object RouteService {

    operator fun get(id: Int): RouteResource? = transaction {
        Route.findById(id)
            ?.toResource()
    }

    fun get(ids: List<Int>, page: Long = 0, limit: Int = 0): List<RouteResource> =
        transaction {
            Route.find { Routes.id inList ids }
                .map { it.toResource() }
        }

    fun create(routes: List<RouteResource>) = transaction {
        Routes
            .batchInsert(routes) { this[Routes.name] = it.name }
            .map { EntityCreatedResource(it[Routes.id].value) }
    }

    fun update(route: RouteResource) = transaction {
        Routes.update({Routes.id eq route.id}) {
            it[name] = route.name
        }
    }
}
package com.portalsoup.ktorexposed.dao

import com.portalsoup.ktorexposed.entity.Route
import com.portalsoup.ktorexposed.entity.RouteTable
import com.portalsoup.ktorexposed.entity.toResource
import com.portalsoup.ktorexposed.resources.EntityCreatedResource
import com.portalsoup.ktorexposed.resources.RouteResource
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.update
import java.lang.RuntimeException

object RouteDAO {

    operator fun get(id: Int): RouteResource {
        val route = Route.findById(id) ?: throw RuntimeException("Route with id=[$id] not found")
        return route.toResource()
    }

    fun get(ids: List<Int>, page: Long = 0, limit: Int = 0): List<RouteResource> =
        Route.find {
            RouteTable.id inList ids
        }.map { it.toResource() }

    fun create(route: RouteResource): EntityCreatedResource = create(listOf(route)).first()

    fun create(routes: List<RouteResource>): List<EntityCreatedResource> = RouteTable
        .batchInsert(routes) {
            this[RouteTable.name] = it.name
        }.map { EntityCreatedResource(it[RouteTable.id].value) }

    fun update(route: RouteResource) = RouteTable
        .update({ RouteTable.id eq route.id}) {
            it[name] = route.name
        }

}
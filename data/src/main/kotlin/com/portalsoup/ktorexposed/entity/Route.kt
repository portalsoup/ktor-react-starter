package com.portalsoup.ktorexposed.entity

import com.portalsoup.ktorexposed.resources.RouteResource
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Routes : IntIdTable() {
    val name = varchar("name", 255)
}

class Route(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Route>(Routes)

    var name by Routes.name
    val coordinates by Coordinate optionalReferrersOn Coordinates.route
}

fun Route.toResource(): RouteResource =
    RouteResource(
        id.value,
        name,
        coordinates.map { it.toResource() }
    )
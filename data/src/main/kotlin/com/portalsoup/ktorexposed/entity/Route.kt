package com.portalsoup.ktorexposed.entity

import com.portalsoup.ktorexposed.resources.RouteResource
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object RouteTable : IntIdTable() {
    val name = varchar("name", 255)
    val owner = reference("traveler", TravelerTable)
}

class Route(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Route>(RouteTable)

    var name by RouteTable.name
    val coordinates by Coordinate optionalReferrersOn CoordinateTable.route
    var owner by Traveler referencedOn CoordinateTable.owner
}

fun Route.toResource(): RouteResource =
    RouteResource(
        id.value,
        name,
        coordinates.map { it.toResource() }
    )
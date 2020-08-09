package com.portalsoup.ktorexposed.entity

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Routes : IntIdTable() {
    val name = varchar("name", 255)
    val coordinates = reference("coordinates", Coordinates).nullable()
}

class Route(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Route>(Routes)

    var name by Routes.name
    val coordinate by Coordinate optionalReferrersOn Routes.coordinates
}
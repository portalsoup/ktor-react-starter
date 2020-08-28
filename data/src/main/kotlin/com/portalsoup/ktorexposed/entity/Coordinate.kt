package com.portalsoup.ktorexposed.entity

import com.portalsoup.ktorexposed.resources.CoordinateResource
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.`java-time`.datetime

object Coordinates: IntIdTable() {
    val lat = float("lat")
    val lng = float("lng")
    val altitude = float("altitude").nullable()
    val routeId = reference("routeid", Routes).nullable()
    val createdDate = datetime("createddate")
    val heartRate = long("heartrate").nullable()
}

class Coordinate(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<Coordinate>(
        Coordinates
    )

    var lat by Coordinates.lat
    var lng  by Coordinates.lng
    var altitude by Coordinates.altitude
    var route by Route optionalReferencedOn Coordinates.routeId
    var createdDate by Coordinates.createdDate
    var heartRate by Coordinates.heartRate
}

fun Coordinate.toResource(): CoordinateResource {
    return CoordinateResource(lat, lng, altitude, route?.id?.value, createdDate, heartRate)
}
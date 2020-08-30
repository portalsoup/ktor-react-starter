package com.portalsoup.ktorexposed.entity

import com.portalsoup.ktorexposed.resources.CoordinateResource
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.`java-time`.datetime

object CoordinateTable: IntIdTable() {
    val lat = float("lat")
    val lng = float("lng")
    val altitude = float("altitude").nullable()
    val routeId = reference("routeid", RouteTable).nullable()
    val createdDate = datetime("createddate")
    val heartRate = long("heartrate").nullable()
}

class Coordinate(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<Coordinate>(
        CoordinateTable
    )

    var lat by CoordinateTable.lat
    var lng  by CoordinateTable.lng
    var altitude by CoordinateTable.altitude
    var route by Route optionalReferencedOn CoordinateTable.routeId
    var createdDate by CoordinateTable.createdDate
    var heartRate by CoordinateTable.heartRate
}

fun Coordinate.toResource(): CoordinateResource {
    return CoordinateResource(lat, lng, altitude, route?.id?.value, createdDate, heartRate)
}
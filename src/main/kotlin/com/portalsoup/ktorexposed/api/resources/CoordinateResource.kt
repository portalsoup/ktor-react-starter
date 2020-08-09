package com.portalsoup.ktorexposed.api.resources

import com.portalsoup.ktorexposed.entity.Coordinates
import com.portalsoup.ktorexposed.entity.Routes
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import java.time.LocalDateTime

data class CoordinateResource(
    var lat: Float,
    var lng: Float,
    var altitude: Float?,
    var routeId: Int,
    var timestamp: LocalDateTime,
    var heartRate: Long
)

fun CoordinateResource.persist(): EntityID<Int> {
    val resource = this

    val foundRouteId = Routes
        .select { Routes.id eq resource.routeId }
        .first()[Routes.id]

    return Coordinates.insert {
        it[lat] = resource.lat
        it[lng] = resource.lng
        it[altitude] = resource.altitude
        it[route] = foundRouteId
        it[createdDate] = resource.timestamp
        it[heartRate] = resource.heartRate
    } get Coordinates.id
}
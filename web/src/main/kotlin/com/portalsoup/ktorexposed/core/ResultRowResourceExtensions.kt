package com.portalsoup.ktorexposed.core

import com.portalsoup.ktorexposed.entity.Coordinates
import com.portalsoup.ktorexposed.entity.Routes
import com.portalsoup.ktorexposed.entity.Travelers
import com.portalsoup.ktorexposed.resources.CoordinateResource
import com.portalsoup.ktorexposed.resources.RouteResource
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select

fun ResultRow.toCoordinate(): CoordinateResource = CoordinateResource(
    this[Coordinates.lat],
    this[Coordinates.lng],
    this[Coordinates.altitude],
    this[Coordinates.route]?.value,
    this[Coordinates.createdDate],
    this[Coordinates.heartRate]
)

fun ResultRow.toPrincipal(): TravelerPrincipal = TravelerPrincipal(
    this[Travelers.id].value,
    this[Travelers.email],
    this[Travelers.passwordHash],
    this[Travelers.passwordSalt]
)

fun ResultRow.toRoute(): RouteResource {
    val routeId = this[Routes.id]
    val coordinates = Coordinates
        .select { Coordinates.id eq routeId }
        .asIterable()
        .map { it.toCoordinate() }

    return RouteResource(
        this[Routes.id].value,
        this[Routes.name],
        coordinates
    )
}
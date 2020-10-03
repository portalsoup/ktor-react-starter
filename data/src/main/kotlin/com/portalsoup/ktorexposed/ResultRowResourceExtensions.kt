package com.portalsoup.ktorexposed

import com.portalsoup.ktorexposed.entity.CoordinateTable
import com.portalsoup.ktorexposed.entity.RouteTable
import com.portalsoup.ktorexposed.entity.TravelerTable
import com.portalsoup.ktorexposed.resources.CoordinateResource
import com.portalsoup.ktorexposed.resources.RouteResource
import com.portalsoup.ktorexposed.resources.TravelerPrincipal
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select

fun ResultRow.toCoordinate(): CoordinateResource = CoordinateResource(
    this[CoordinateTable.lat],
    this[CoordinateTable.lng],
    this[CoordinateTable.altitude],
    this[CoordinateTable.route]?.value,
    this[CoordinateTable.createdDate],
    this[CoordinateTable.heartRate]
)

fun ResultRow.toPrincipal(): TravelerPrincipal = TravelerPrincipal(
    this[TravelerTable.id].value,
    this[TravelerTable.email],
    this[TravelerTable.passwordHash],
    this[TravelerTable.passwordSalt]
)

fun ResultRow.toRoute(): RouteResource {
    val routeId = this[RouteTable.id]
    val coordinates = CoordinateTable
        .select { CoordinateTable.id eq routeId }
        .asIterable()
        .map { it.toCoordinate() }

    return RouteResource(
        this[RouteTable.id].value,
        this[RouteTable.name],
        coordinates
    )
}
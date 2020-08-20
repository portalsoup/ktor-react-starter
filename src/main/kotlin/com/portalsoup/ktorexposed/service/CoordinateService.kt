package com.portalsoup.ktorexposed.service

import com.portalsoup.ktorexposed.api.resources.CoordinateResource
import com.portalsoup.ktorexposed.api.resources.EntityCreatedResource
import com.portalsoup.ktorexposed.entity.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object CoordinateService {

    operator fun get(id: Int): CoordinateResource? {
        return transaction {
            Coordinate
                .findById(id)
                ?.toResource()
        }
    }

    fun get(ids: List<Int>, page: Long = 0, limit: Int = 0): List<CoordinateResource> =
        transaction {
            Coordinate.find { Coordinates.id inList ids }
                .map { it.toResource() }
        }

    fun create(coordinates: List<CoordinateResource>) = transaction {

        Coordinates
            .batchInsert(coordinates) {
                val rawRouteId = it.routeId
                // TODO Can I actually write new queries inside this closure?
                val routeId = if (rawRouteId != null) Route.findById(rawRouteId)?.id else null

                this[Coordinates.lat] = it.lat
                this[Coordinates.lng] = it.lng
                this[Coordinates.altitude] = it.altitude
                this[Coordinates.route] = routeId
                this[Coordinates.createdDate] = it.timestamp
                this[Coordinates.heartRate] = it.heartRate
            }
            .map { EntityCreatedResource(it[Coordinates.id].value) }
    }

    fun update(coordinate: Coordinate) = transaction {
        Coordinates.update { coordinate }
    }
}
package com.portalsoup.ktorexposed.dao

import com.portalsoup.ktorexposed.entity.Coordinate
import com.portalsoup.ktorexposed.entity.CoordinateTable
import com.portalsoup.ktorexposed.entity.Route
import com.portalsoup.ktorexposed.entity.toResource
import com.portalsoup.ktorexposed.resources.CoordinateResource
import com.portalsoup.ktorexposed.resources.EntityCreatedResource
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.update

object CoordinateDAO {

    operator fun get(id: Int): CoordinateResource? = Coordinate
        .findById(id)
        ?.toResource()

    fun get(ids: List<Int>, page: Long = 0, limit: Int = 0): List<CoordinateResource> =
        Coordinate.find { CoordinateTable.id inList ids }
            .map { it.toResource() }

    fun create(coordinates: List<CoordinateResource>) = CoordinateTable
            .batchInsert(coordinates) {
                val rawRouteId = it.routeId
                // TODO Can I actually write new queries inside this closure?
                val routeId = if (rawRouteId != null) Route.findById(rawRouteId)?.id else null

                this[CoordinateTable.lat] = it.lat
                this[CoordinateTable.lng] = it.lng
                this[CoordinateTable.altitude] = it.altitude
                this[CoordinateTable.route] = routeId
                this[CoordinateTable.createdDate] = it.timestamp
                this[CoordinateTable.heartRate] = it.heartRate
            }
            .map { EntityCreatedResource(it[CoordinateTable.id].value) }


    fun update(coordinate: Coordinate) = CoordinateTable.update { coordinate }

}
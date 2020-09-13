package com.portalsoup.ktorexposed.core.service


import com.portalsoup.ktorexposed.dao.CoordinateDAO
import com.portalsoup.ktorexposed.dao.RouteDAO
import com.portalsoup.ktorexposed.resources.CoordinateResource
import com.portalsoup.ktorexposed.resources.RouteResource
import io.jenetics.jpx.GPX
import io.jenetics.jpx.TrackSegment
import io.jenetics.jpx.WayPoint
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.InputStream
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.streams.toList

object GPXService {

    fun parseGpxInputStream(inputStream: InputStream): GPX = GPX.read(inputStream)

    fun importGpx(gpx: GPX): List<RouteResource> = gpx.tracks().toList()
        .map { track ->
            val i = AtomicInteger(0)
            val uuid = UUID.randomUUID().toString()
            val getName = { "${uuid}_$i" }
            track.segments
                .map { mapAndPersistPoints(it) }
                .map { points ->
                    val route = RouteResource(null, track.name.orElseGet(getName), points)
                    val id = transaction {
                        RouteDAO.create(
                            listOf(route)
                        ).first()
                    }
                    route.copy(id = id.id, coordinates = points.map { it.copy(routeId = id.id) })
                }
        }.flatten()

    fun mapAndPersistPoints(trackSegment: TrackSegment): List<CoordinateResource> {
        val points = trackSegment.points.map { this.mapToCoordinate(it) }
        transaction {
            CoordinateDAO.create(points)
        }
        return points
    }

    private fun mapToCoordinate(wayPoint: WayPoint): CoordinateResource {
        return CoordinateResource(
            wayPoint.latitude.toFloat(),
            wayPoint.longitude.toFloat(),
            wayPoint.elevation.map{ it.toFloat() }.orElse(null),
            null,
            wayPoint.time.map { it.toEpochSecond() }.map{ LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC) }.orElse(LocalDateTime.now()),
            null
        )
    }
}

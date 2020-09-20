package com.portalsoup.ktorexposed.core.service


import com.portalsoup.ktorexposed.core.util.getLogger
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

    val log = getLogger(GPXService.javaClass)

    fun parseGpxInputStream(inputStream: InputStream): GPX = GPX.read(inputStream)

    fun importGpx(gpx: GPX): List<RouteResource> = gpx.tracks().toList()
        .map { track ->
            val i = AtomicInteger(0)
            val uuid = UUID.randomUUID().toString()
            val getName = { "${uuid}_$i" }
            transaction {
                val routeCreated = RouteDAO.create(RouteResource(null, track.name.orElseGet(getName)))
                log.debug("Route created $routeCreated")
                track.segments.map { mapAndPersistPoints(routeCreated.id, it) }
                listOf(RouteDAO[routeCreated.id])
            }
        }.flatten()

    fun mapAndPersistPoints(routeId: Int, trackSegment: TrackSegment): List<CoordinateResource> {
        val points = trackSegment.points.map { this.mapToCoordinate(routeId, it) }
        transaction {
            CoordinateDAO.create(points)
        }
        return points
    }

    private fun mapToCoordinate(routeId: Int, wayPoint: WayPoint): CoordinateResource {
        return CoordinateResource(
            wayPoint.latitude.toFloat(),
            wayPoint.longitude.toFloat(),
            wayPoint.elevation.map { it.toFloat() }.orElse(null),
            routeId,
            wayPoint.time.map { it.toEpochSecond() }.map{ LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC) }.orElse(LocalDateTime.now()),
            null
        )
    }
}

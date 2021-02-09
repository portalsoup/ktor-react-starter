package com.portalsoup.ktorexposed.core.service


import com.portalsoup.ktorexposed.dao.CoordinateDAO
import com.portalsoup.ktorexposed.dao.RouteDAO
import com.portalsoup.ktorexposed.entity.Route
import com.portalsoup.ktorexposed.entity.toResource
import com.portalsoup.ktorexposed.resources.CoordinateResource
import com.portalsoup.ktorexposed.resources.CurrentUserResource
import com.portalsoup.ktorexposed.resources.RouteResource
import com.portalsoup.ktorexposed.resources.TravelerResource
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

    fun importGpx(gpx: GPX, owner: TravelerResource): List<RouteResource> = gpx.tracks().toList()
        .map { track ->
            val i = AtomicInteger(0)
            val uuid = UUID.randomUUID().toString()
            val getName = { "${uuid}_$i" }
            transaction {
                val routeCreated = RouteDAO.create(RouteResource(null, owner, track.name.orElseGet(getName)))
                track.segments.map { mapAndPersistPoints(routeCreated, it, owner) }
                listOf(RouteDAO[routeCreated.id.value])
                    .map { it.toResource() }
            }
        }.flatten()

    fun mapAndPersistPoints(route: Route, trackSegment: TrackSegment, owner: TravelerResource): List<CoordinateResource> {
        val points = trackSegment.points.map { this.mapToCoordinate(route, it, owner) }
        transaction {
            CoordinateDAO.create(points)
        }
        return points
    }

    private fun mapToCoordinate(route: Route, wayPoint: WayPoint, owner: TravelerResource): CoordinateResource {
        return CoordinateResource(
            owner,
            wayPoint.latitude.toFloat(),
            wayPoint.longitude.toFloat(),
            wayPoint.elevation.map { it.toFloat() }.orElse(null),
            route.id.value,
            wayPoint.time.map { it.toEpochSecond() }.map{ LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC) }.orElse(LocalDateTime.now()),
            null
        )
    }
}

package com.portalsoup.ktorexposed.api.resources

import com.portalsoup.ktorexposed.entity.Coordinates
import com.portalsoup.ktorexposed.entity.Routes
import com.portalsoup.ktorexposed.entity.Traveler
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import java.time.LocalDateTime

data class CoordinateResource(
    val lat: Float,
    val lng: Float,
    val altitude: Float?,
    val routeId: Int?,
    val timestamp: LocalDateTime,
    val heartRate: Long?
)
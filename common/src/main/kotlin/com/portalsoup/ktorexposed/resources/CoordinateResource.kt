package com.portalsoup.ktorexposed.resources

import java.time.LocalDateTime

data class CoordinateResource(
    val lat: Float,
    val lng: Float,
    val altitude: Float?,
    val routeId: Int?,
    val timestamp: LocalDateTime,
    val heartRate: Long?
)
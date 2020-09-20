package com.portalsoup.ktorexposed.resources

data class RouteResource(
    val id: Int? = null,
    val name: String,
    val coordinates: List<CoordinateResource>? = null
)

package com.portalsoup.ktorexposed.resources

data class RouteResource(
    val id: Int? = null,
    val owner: TravelerResource?,
    val name: String,
    val coordinates: List<CoordinateResource>? = null
)

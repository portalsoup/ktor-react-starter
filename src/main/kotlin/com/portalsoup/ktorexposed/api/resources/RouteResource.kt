package com.portalsoup.ktorexposed.api.resources

import com.portalsoup.ktorexposed.entity.Coordinates
import com.portalsoup.ktorexposed.entity.Routes
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

data class RouteResource(
    val id: Int? = null,
    val name: String,
    val coordinates: List<CoordinateResource>
)

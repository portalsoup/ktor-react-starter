package com.portalsoup.ktorexposed.core.service

import com.portalsoup.ktorexposed.dao.RouteDAO
import com.portalsoup.ktorexposed.entity.Route
import com.portalsoup.ktorexposed.entity.toResource
import com.portalsoup.ktorexposed.resources.RouteResource
import org.jetbrains.exposed.sql.transactions.transaction

object RouteService {
    fun getRoute(id: Int?): RouteResource? = transaction {
        id?.let { RouteDAO[it].toResource() }
    }

    fun create(Route: List<RouteResource>): List<Route> = transaction {
        RouteDAO.create(Route).toList()
    }
}
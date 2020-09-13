package com.portalsoup.ktorexposed.core.service

import com.portalsoup.ktorexposed.dao.RouteDAO
import com.portalsoup.ktorexposed.resources.EntityCreatedResource
import com.portalsoup.ktorexposed.resources.RouteResource
import org.jetbrains.exposed.sql.transactions.transaction

object RouteService {
    fun getRoute(id: Int): RouteResource? = transaction {
        RouteDAO[id]
    }

    fun create(Route: List<RouteResource>): List<EntityCreatedResource> = transaction {
        RouteDAO.create(Route)
    }
}
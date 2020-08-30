package com.portalsoup.ktorexposed.core.service

import com.portalsoup.ktorexposed.dao.RouteDAO
import com.portalsoup.ktorexposed.resources.EntityCreatedResource
import com.portalsoup.ktorexposed.resources.RouteResource

object RouteService {
    fun getRoute(id: Int): RouteResource? = RouteDAO[id]

    fun create(Route: List<RouteResource>): List<EntityCreatedResource> = RouteDAO.create(Route)
}
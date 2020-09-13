package com.portalsoup.ktorexposed.core.service

import com.portalsoup.ktorexposed.dao.CoordinateDAO
import com.portalsoup.ktorexposed.resources.CoordinateResource
import com.portalsoup.ktorexposed.resources.EntityCreatedResource
import org.jetbrains.exposed.sql.transactions.transaction

object CoordinateService {

    fun getCoordinate(id: Int): CoordinateResource? = transaction { CoordinateDAO[id] }

    fun create(coordinate: List<CoordinateResource>): List<EntityCreatedResource> = transaction { CoordinateDAO.create(coordinate) }
}
package com.portalsoup.ktorexposed.core.service

import com.portalsoup.ktorexposed.dao.CoordinateDAO
import com.portalsoup.ktorexposed.entity.Coordinate
import com.portalsoup.ktorexposed.entity.toResource
import com.portalsoup.ktorexposed.resources.CoordinateResource
import org.jetbrains.exposed.sql.transactions.transaction

object CoordinateService {

    fun getCoordinate(id: Int): CoordinateResource? = transaction { CoordinateDAO[id]?.toResource() }

    fun create(coordinate: List<CoordinateResource>): List<Coordinate> = transaction { CoordinateDAO.create(coordinate).toList() }
}
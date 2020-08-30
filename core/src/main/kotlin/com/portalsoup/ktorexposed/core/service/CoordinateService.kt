package com.portalsoup.ktorexposed.core.service

import com.portalsoup.ktorexposed.dao.CoordinateDAO
import com.portalsoup.ktorexposed.resources.CoordinateResource
import com.portalsoup.ktorexposed.resources.EntityCreatedResource

object CoordinateService {

    fun getCoordinate(id: Int): CoordinateResource? = CoordinateDAO[id]

    fun create(coordinate: List<CoordinateResource>): List<EntityCreatedResource> = CoordinateDAO.create(coordinate)
}
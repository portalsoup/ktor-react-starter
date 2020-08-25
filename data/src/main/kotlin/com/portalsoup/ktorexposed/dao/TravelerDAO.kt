package com.portalsoup.ktorexposed.dao

import com.portalsoup.ktorexposed.core.resource.TravelerPrincipal
import com.portalsoup.ktorexposed.entity.Traveler
import com.portalsoup.ktorexposed.entity.Travelers
import com.portalsoup.ktorexposed.entity.toAuthResource
import com.portalsoup.ktorexposed.entity.toResource
import com.portalsoup.ktorexposed.resources.EntityCreatedResource
import com.portalsoup.ktorexposed.resources.TravelerResource
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

object TravelerDAO {
    operator fun get(id: Int): TravelerResource? = transaction {
        Traveler.findById(id)
            ?.toResource()
    }

    fun get(ids: List<Int>, page: Long = 0, limit: Int = 0): List<TravelerResource> =
        transaction {
            Traveler.find { Travelers.id inList ids }
                .map { it.toResource() }
        }

    fun getWithAuth(id: Int): TravelerResource? = transaction {
        Traveler.findById(id)
            ?.toAuthResource()
    }

    fun create(travelers: List<TravelerPrincipal>) = transaction {
        Travelers
            .batchInsert(travelers) { this[Travelers.email] = it.email }
            .map { EntityCreatedResource(it[Travelers.id].value) }
    }

    fun update(traveler: TravelerResource) = transaction {
        Travelers.update({ Travelers.id eq traveler.id}) {
            it[email] = traveler.email
        }
    }
}
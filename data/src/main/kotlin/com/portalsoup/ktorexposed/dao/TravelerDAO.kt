package com.portalsoup.ktorexposed.dao

import com.portalsoup.ktorexposed.entity.Traveler
import com.portalsoup.ktorexposed.entity.TravelerTable
import com.portalsoup.ktorexposed.entity.toResource
import com.portalsoup.ktorexposed.resources.TravelerPrincipal
import com.portalsoup.ktorexposed.resources.TravelerResource
import org.jetbrains.exposed.sql.update

object TravelerDAO {
    operator fun get(id: Int): Traveler? = Traveler.findById(id)

    fun get(ids: List<Int>, page: Long = 0, limit: Int = 0): List<Traveler> = Traveler
        .find {
            TravelerTable.id inList ids
        }.toList()


    fun getWithAuth(id: Int): TravelerResource? = Traveler
        .findById(id)
        ?.toResource()


    fun create(traveler: TravelerPrincipal): Traveler = Traveler.new {
        email = traveler.email
        passwordHash = traveler.passwordHash
        passwordSalt = traveler.passwordSalt
    }

    fun update(traveler: TravelerResource) = TravelerTable
        .update({ TravelerTable.id eq traveler.id}) {
            it[email] = traveler.email
        }
}

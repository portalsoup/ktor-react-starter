package com.portalsoup.ktorexposed.dao

import com.portalsoup.ktorexposed.entity.Traveler
import com.portalsoup.ktorexposed.entity.TravelerTable
import com.portalsoup.ktorexposed.entity.toPrincipal
import com.portalsoup.ktorexposed.entity.toResource
import com.portalsoup.ktorexposed.resources.TravelerPrincipal
import com.portalsoup.ktorexposed.resources.TravelerResource
import org.jetbrains.exposed.sql.update
import java.lang.RuntimeException

object TravelerDAO {
    operator fun get(id: Int): Traveler? = Traveler.findById(id)

    fun get(ids: List<Int>, page: Long = 0, limit: Int = 0): List<Traveler> = Traveler
        .find {
            TravelerTable.id inList ids
        }.toList()


    fun getWithAuth(id: Int): TravelerPrincipal? = Traveler
        .findById(id)
        ?.toPrincipal()


    fun create(traveler: TravelerResource): Traveler = Traveler.new {
        email = traveler.email
        passwordHash = traveler.passwordHash ?: throw RuntimeException("Need a password hash to create user")
        passwordSalt = traveler.passwordSalt ?: throw RuntimeException("Need a password salt to create user")
    }

    fun update(traveler: TravelerResource) = TravelerTable
        .update({ TravelerTable.id eq traveler.id}) {
            it[email] = traveler.email
        }
}

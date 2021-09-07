package com.portalsoup.ktorexposed.core.data.dao

import com.portalsoup.ktorexposed.core.data.entity.UserAccount
import com.portalsoup.ktorexposed.core.data.entity.UserTable
import com.portalsoup.ktorexposed.core.data.entity.toPrincipal
import com.portalsoup.ktorexposed.core.data.entity.toResource
import com.portalsoup.ktorexposed.core.resources.UserPrincipal
import com.portalsoup.ktorexposed.core.resources.UserResource
import org.jetbrains.exposed.sql.update
import java.lang.RuntimeException

object UserDAO {
    operator fun get(id: Int): UserAccount? = UserAccount.findById(id)

    fun get(ids: List<Int>, page: Long = 0, limit: Int = 0): List<UserAccount> = UserAccount
        .find {
            UserTable.id inList ids
        }.toList()

    fun getByEmail(email: String) = UserAccount.find { UserTable.email eq email }.first()

    fun getAsPrincipal(id: Int): UserPrincipal? = UserAccount
        .findById(id)
        ?.toPrincipal()

    fun incrementLoggedInCount(id: Int): Boolean {
        val user = get(id)?.toResource() ?: return false
        val updatedUser = user.copy(timesLoggedIn = user.timesLoggedIn + 1)
        update(updatedUser)
        println("user logged in count incremented ${updatedUser.timesLoggedIn}")
        return true
    }

    fun create(user: UserResource): UserAccount = UserAccount.new {
        email = user.email
        passwordHash = user.passwordHash ?: throw RuntimeException("Need a password hash to create user")
        passwordSalt = user.passwordSalt ?: throw RuntimeException("Need a password salt to create user")
        timesLoggedIn = user.timesLoggedIn
    }

    fun update(user: UserResource) = UserTable
        .update({ UserTable.id eq user.id}) {
            it[email] = user.email
            it[timesLoggedIn] = user.timesLoggedIn
        }
}

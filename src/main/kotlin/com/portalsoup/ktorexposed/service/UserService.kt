package com.portalsoup.ktorexposed.service

import com.portalsoup.ktorexposed.entity.User
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.UpdateStatement
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

// Resources
data class FullNameResource(val firstName: String, val lastName: String) {
    override fun toString(): String = "$firstName $lastName"
}

// Resource mapping functions
private fun ResultRow.toFullName(): FullNameResource = FullNameResource(this[User.firstName], this[User.lastName])
private fun UpdateStatement.fromFullName(fullName: FullNameResource) {
    this[User.firstName] = fullName.firstName
    this[User.lastName] = fullName.lastName
}


// Service functions
fun fullName(id: Int): FullNameResource? = transaction {
    User
        .select { User.id eq id }
        .single()
        .toFullName()
}

fun updateFullName(id: Int, fullName: FullNameResource) {
    User.update({User.id eq id}) { it: UpdateStatement -> it.fromFullName(fullName) }
}

fun listNames(): List<String> = transaction {
    User.selectAll().map {
        "${it[User.firstName]} ${it[User.lastName]}"
    }
}
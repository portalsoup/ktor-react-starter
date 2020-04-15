package com.portalsoup.ktorexposed.service

import com.portalsoup.ktorexposed.entity.User
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

data class FullNameResource(val firstName: String, val lastName: String) {
    override fun toString(): String = "$firstName $lastName"
}

private fun ResultRow.toFullName(): FullNameResource = FullNameResource(this[User.firstName], this[User.lastName])

fun fullName(id: Int): FullNameResource? = transaction {
    User
        .select { User.id eq id }
        .single()
        .toFullName()
}

fun printAllNames() = transaction {
    User.selectAll().map {
        "${it[User.firstName]} ${it[User.lastName]}"
    }
}.forEach {
    println(it)
}
package com.portalsoup.ktorexposed.entity

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.`java-time`.datetime

object BlogPosts : IntIdTable() {
    val title = varchar("title", 255)
    val body = text("body")
    val routeId = reference("route", Routes).nullable()
    val timePosted = datetime("createddate")
}

class BlogPost(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<BlogPost>(BlogPosts)

    var title by BlogPosts.title
    var body by BlogPosts.body
    var routeId by Route optionalReferencedOn BlogPosts.routeId
}
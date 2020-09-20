package com.portalsoup.ktorexposed.entity

import com.portalsoup.ktorexposed.resources.BlogPostResource
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.`java-time`.datetime

object BlogPostTable : IntIdTable() {
    val title = varchar("title", 255)
    val body = text("body")
    val route = reference("route", RouteTable).nullable()
    val timePosted = datetime("createddate")
}

class BlogPost(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<BlogPost>(BlogPostTable)

    var title by BlogPostTable.title
    var body by BlogPostTable.body
    var route by Route optionalReferencedOn BlogPostTable.route
    var timePosted by BlogPostTable.timePosted
}

fun BlogPost.toResource(): BlogPostResource =
    BlogPostResource(
        id,
        title,
        body,
        route?.toResource(),
        timePosted
    )
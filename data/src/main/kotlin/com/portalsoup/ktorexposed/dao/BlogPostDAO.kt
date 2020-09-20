package com.portalsoup.ktorexposed.dao

import com.portalsoup.ktorexposed.entity.BlogPost
import com.portalsoup.ktorexposed.entity.BlogPostTable
import com.portalsoup.ktorexposed.entity.toResource
import com.portalsoup.ktorexposed.resources.BlogPostResource
import com.portalsoup.ktorexposed.resources.EntityCreatedResource
import org.jetbrains.exposed.sql.batchInsert

object BlogPostDAO {

    operator fun get(id: Int): BlogPost? = BlogPost
        .findById(id)

    fun get(ids: List<Int>, page: Long = 0, limit: Int = 0): List<BlogPost> = BlogPost
        .find {
            BlogPostTable.id inList ids
        }.toList()
    
    fun create(blogPosts: List<BlogPostResource>): List<EntityCreatedResource> {
        return BlogPostTable.batchInsert(blogPosts) {
            this[BlogPostTable.title] = it.title
            this[BlogPostTable.body] = it.body
            this[BlogPostTable.route] = it.route?.id?.let { id -> RouteDAO[id].id } // lot of db queries?
            this[BlogPostTable.timePosted] = it.timePosted
        }
            .map { EntityCreatedResource(it[BlogPostTable.id].value) }
    }

}
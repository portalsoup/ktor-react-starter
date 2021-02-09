package com.portalsoup.ktorexposed.dao

import com.portalsoup.ktorexposed.entity.*
import com.portalsoup.ktorexposed.resources.CurrentUserResource
import com.portalsoup.ktorexposed.resources.blogpost.BlogPostResource
import com.portalsoup.ktorexposed.resources.EntityCreatedResource
import com.portalsoup.ktorexposed.resources.TravelerResource
import com.portalsoup.ktorexposed.resources.blogpost.BlogPostListResource
import org.jetbrains.exposed.sql.batchInsert
import java.lang.RuntimeException
import java.time.LocalDateTime

object BlogPostDAO {

    operator fun get(id: Int): BlogPost? = BlogPost
        .findById(id)

    fun get(ids: List<Int>, page: Long = 0, limit: Int = 0): List<BlogPost> = BlogPost
        .find {
            BlogPostTable.id inList ids
        }.toList()
    
    fun create(blogPosts: BlogPostListResource, owner: CurrentUserResource): List<EntityCreatedResource> {
        return BlogPostTable.batchInsert(blogPosts.blogPosts) {
            this[BlogPostTable.title] = it.title
            this[BlogPostTable.body] = it.body
            this[BlogPostTable.route] = it.route?.id?.let { id -> RouteDAO[id].id } // lot of db queries?
            this[BlogPostTable.timePosted] = LocalDateTime.now()
            this[BlogPostTable.owner] = TravelerDAO[owner.id]?.id ?: throw RuntimeException("Failed to lookup owner id")
        }
            .map { EntityCreatedResource(it[BlogPostTable.id].value) }
    }

}
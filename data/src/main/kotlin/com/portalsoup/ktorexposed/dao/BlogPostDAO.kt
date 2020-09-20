package com.portalsoup.ktorexposed.dao

import com.portalsoup.ktorexposed.entity.BlogPost
import com.portalsoup.ktorexposed.entity.BlogPostTable
import com.portalsoup.ktorexposed.entity.toResource
import com.portalsoup.ktorexposed.resources.BlogPostResource
import com.portalsoup.ktorexposed.resources.EntityCreatedResource
import org.jetbrains.exposed.sql.batchInsert

object BlogPostDAO {

    operator fun get(id: Int): BlogPostResource? = BlogPost
        .findById(id)
        ?.toResource()

    fun get(ids: List<Int>, page: Long = 0, limit: Int = 0): List<BlogPostResource> = BlogPost
        .find {
            BlogPostTable.id inList ids
        }.map { it.toResource() }
    
    fun create(blogPosts: List<BlogPostResource>) = BlogPostTable.batchInsert(blogPosts) {
        this[BlogPostTable.title] = it.title
        this[BlogPostTable.body] = it.body
        this[BlogPostTable.route] = null // it.route?.id // TODO janky hack won't work
        this[BlogPostTable.timePosted] = it.timePosted
    }
        .map { EntityCreatedResource(it[BlogPostTable.id].value) }

}
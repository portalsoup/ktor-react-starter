package com.portalsoup.ktorexposed.dao

import com.portalsoup.ktorexposed.entity.BlogPost
import com.portalsoup.ktorexposed.entity.BlogPosts
import com.portalsoup.ktorexposed.entity.Coordinates
import com.portalsoup.ktorexposed.entity.toResource
import com.portalsoup.ktorexposed.resources.BlogPostResource
import com.portalsoup.ktorexposed.resources.EntityCreatedResource
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.transactions.transaction

object BlogPostDAO {

    operator fun get(id: Int): BlogPostResource? = BlogPost
        .findById(id)
        ?.toResource()

    fun get(ids: List<Int>, page: Long = 0, limit: Int = 0): List<BlogPostResource> = BlogPost
        .find {
            BlogPosts.id inList ids
        }.map { it.toResource() }
    
    fun create(blogPosts: List<BlogPostResource>) = BlogPosts.batchInsert(blogPosts) {
        this[BlogPosts.title] = it.title
        this[BlogPosts.body] = it.body
        this[BlogPosts.routeId] = null // it.route?.id // TODO janky hack won't work
        this[BlogPosts.timePosted] = it.timePosted
    }
        .map { EntityCreatedResource(it[BlogPosts.id].value) }

}
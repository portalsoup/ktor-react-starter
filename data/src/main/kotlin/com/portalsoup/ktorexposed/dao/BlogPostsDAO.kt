package com.portalsoup.ktorexposed.dao

import com.portalsoup.ktorexposed.entity.BlogPosts
import com.portalsoup.ktorexposed.resources.BlogPostResource
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.transactions.transaction

object BlogPostsDAO {

    fun create(blogPosts: List<BlogPostResource>) = transaction {
        BlogPosts.batchInsert(blogPosts) {
            this[BlogPosts.title] = it.title
            this[BlogPosts.body] = it.body
            this[BlogPosts.routeId] = null // it.route?.id // TODO janky hack won't work
            this[BlogPosts.timePosted] = it.timePosted
        }
    }
}
package com.portalsoup.ktorexposed.service

import com.portalsoup.ktorexposed.api.resources.BlogPostResource
import com.portalsoup.ktorexposed.entity.BlogPost
import com.portalsoup.ktorexposed.entity.BlogPosts
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.transactions.transactionScope

object BlogPostService {
    fun create(blogPosts: List<BlogPostResource>) = transaction {
        BlogPosts.batchInsert(blogPosts) {
            this[BlogPosts.title] = it.title
            this[BlogPosts.body] = it.body
            this[BlogPosts.routeId] = it.route?.id
            this[BlogPosts.timePosted] = it.timePosted
        }
    }
}
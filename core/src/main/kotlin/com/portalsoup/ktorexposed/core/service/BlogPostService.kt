package com.portalsoup.ktorexposed.core.service

import com.portalsoup.ktorexposed.dao.BlogPostDAO
import com.portalsoup.ktorexposed.resources.BlogPostResource
import com.portalsoup.ktorexposed.resources.EntityCreatedResource
import org.jetbrains.exposed.sql.transactions.transaction


object BlogPostService {
    fun getBlogPost(id: Int): BlogPostResource? = transaction { BlogPostDAO[id] }

    fun create(blogPost: List<BlogPostResource>): List<EntityCreatedResource> = transaction { BlogPostDAO.create(blogPost) }
}
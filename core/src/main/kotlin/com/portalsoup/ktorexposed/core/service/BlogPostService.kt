package com.portalsoup.ktorexposed.core.service

import com.portalsoup.ktorexposed.dao.BlogPostDAO
import com.portalsoup.ktorexposed.entity.BlogPost
import com.portalsoup.ktorexposed.entity.BlogPostTable
import com.portalsoup.ktorexposed.entity.toResource
import com.portalsoup.ktorexposed.resources.BlogPostResource
import com.portalsoup.ktorexposed.resources.EntityCreatedResource
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction


object BlogPostService {
    fun getBlogPost(id: Int): BlogPostResource? = transaction { BlogPostDAO[id]?.toResource() }

    fun create(blogPost: List<BlogPostResource>): List<EntityCreatedResource> = transaction { BlogPostDAO.create(blogPost) }

    fun allBlogPosts(): List<BlogPostResource> {
        return transaction {
            BlogPostTable.selectAll()
                .toList()
                .let { BlogPost.wrapRows(SizedCollection(it)) }
                .map { it.toResource() }
        }
    }
}
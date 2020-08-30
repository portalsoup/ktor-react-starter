package com.portalsoup.ktorexposed.core.service

import com.portalsoup.ktorexposed.dao.BlogPostDAO
import com.portalsoup.ktorexposed.resources.BlogPostResource
import com.portalsoup.ktorexposed.resources.EntityCreatedResource


object BlogPostService {
    fun getBlogPost(id: Int): BlogPostResource? = BlogPostDAO[id]

    fun create(blogPost: List<BlogPostResource>): List<EntityCreatedResource> = BlogPostDAO.create(blogPost)
}
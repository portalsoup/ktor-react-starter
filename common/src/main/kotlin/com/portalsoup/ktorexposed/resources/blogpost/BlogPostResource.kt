package com.portalsoup.ktorexposed.resources.blogpost

import com.portalsoup.ktorexposed.resources.RouteResource
import com.portalsoup.ktorexposed.resources.TravelerResource
import java.time.LocalDateTime

data class BlogPostResource(
    val id: Int?,
    val owner: TravelerResource,
    val title: String,
    val body: String,
    val route: RouteResource?,
    val timePosted: LocalDateTime = LocalDateTime.now()
)

data class BlogPostListResource(
    val blogPosts: List<BlogPostResource>
)

fun BlogPostResource.toListResource() = BlogPostListResource(listOf(this))
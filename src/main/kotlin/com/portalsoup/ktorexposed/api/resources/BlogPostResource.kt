package com.portalsoup.ktorexposed.api.resources

import com.portalsoup.ktorexposed.entity.Route
import java.time.LocalDateTime

data class BlogPostResource(
    val id: Int?,
    val title: String,
    val body: String,
    val route: Route?,
    val timePosted: LocalDateTime
)
package com.portalsoup.ktorexposed.resources

import java.time.LocalDateTime

data class BlogPostResource(
    val id: Int?,
    val owner: TravelerResource?,
    val title: String,
    val body: String,
    val route: RouteResource?,
    val timePosted: LocalDateTime
)
package com.portalsoup.ktorexposed.core.service

import com.portalsoup.ktorexposed.core.monad.Try
import com.portalsoup.ktorexposed.dao.BlogPostDAO
import com.portalsoup.ktorexposed.entity.BlogPost
import com.portalsoup.ktorexposed.entity.BlogPostTable
import com.portalsoup.ktorexposed.entity.toResource
import com.portalsoup.ktorexposed.resources.*
import com.portalsoup.ktorexposed.resources.blogpost.BlogPostResource
import com.portalsoup.ktorexposed.resources.blogpost.BlogPostListResource
import com.portalsoup.ktorexposed.resources.blogpost.toListResource
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.Exception


object BlogPostService {
    fun getBlogPost(id: Int): BlogPostResource? = transaction { BlogPostDAO[id]?.toResource() }

    fun create(blogPost: BlogPostListResource, owner: CurrentUserResource): List<EntityCreatedResource> = transaction { BlogPostDAO.create(blogPost, owner) }

    fun allBlogPosts(): List<BlogPostResource> {
        return transaction {
            BlogPostTable.selectAll()
                .toList()
                .let { BlogPost.wrapRows(SizedCollection(it)) }
                .map { it.toResource() }
        }
    }

    suspend fun parseMultipartBlogPost(multipart: MultiPartData, user: TravelerResource): Try<BlogPostResource> {
        try {
            var title = ""
            var body = ""
            var gpx: RouteResource? = null


            multipart.forEachPart {
                when (it) {
                    is PartData.FileItem -> {
                        val rawGpx = GPXService.parseGpxInputStream(it.streamProvider())
                        gpx = GPXService.importGpx(rawGpx, user)[0]
                    }
                    is PartData.FormItem -> {
                        if (it.name == "title") {
                            title = it.value
                        } else if (it.name == "body") {
                            body = it.value
                        }
                    }
                    else -> Unit
                }
            }

            val post = BlogPostResource(
                id = null,
                owner = user,
                title = title,
                body = body,
                route = gpx
            )

            return Try.Success(post)
        } catch (e: Exception) {
            return Try.Failure(e)
        }
    }
}
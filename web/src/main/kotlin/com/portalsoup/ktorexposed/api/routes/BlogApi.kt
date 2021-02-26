package com.portalsoup.ktorexposed.api.routes

import com.portalsoup.ktorexposed.api.BaseApi
import com.portalsoup.ktorexposed.core.monad.Try.Failure
import com.portalsoup.ktorexposed.core.monad.Try.Success
import com.portalsoup.ktorexposed.core.service.BlogPostService
import com.portalsoup.ktorexposed.core.service.GPXService
import com.portalsoup.ktorexposed.resources.RouteResource
import com.portalsoup.ktorexposed.resources.blogpost.BlogPostListResource
import com.portalsoup.ktorexposed.resources.blogpost.BlogPostResource
import com.portalsoup.ktorexposed.resources.blogpost.toListResource
import com.portalsoup.ktorexposed.resources.toResource
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.io.File
import java.util.*

object BlogApi : BaseApi {


    fun Route.blog() {
        route("/blog") {
            get("/all") {
                val posts = when (val maybeUser = withIdentity(call) { it }) {
                    is Success -> BlogPostService.allBlogPosts()
                    is Failure -> BlogPostService.allBlogPosts()
                }
                call.respond(posts)
            }

            get("/{id}") {
                val id = call.parameters["id"]?.toInt()
                    ?: throw RuntimeException("No valid ID found.")
                val post = BlogPostService.getBlogPost(id)
                post?.let { call.respond(it) }
            }

            authenticate {

                post("/new") {

                    when (val maybeUser = withIdentity(call) { it }) {
                        is Success -> {
                            call.runCatching { receiveMultipart() }
                                .onSuccess { multipart ->
                                    val maybeNewPost = BlogPostService
                                        .parseMultipartBlogPost(multipart, maybeUser.data.toResource())
                                    println("\n\n$maybeNewPost\n\n")

                                    when (maybeNewPost) {
                                        is Success -> {
                                            val created = BlogPostService.create(maybeNewPost.data.toListResource(), maybeUser.data)
                                            call.respond(created)
                                        }
                                        is Failure -> call.respond(HttpStatusCode.BadRequest)
                                    }
                                }
                                .onFailure { println("Failed to parse multipart... ${it.message}") }
                        }
                        is Failure -> call.respond(HttpStatusCode.BadRequest)
                    }
                }
            }
        }
    }
}
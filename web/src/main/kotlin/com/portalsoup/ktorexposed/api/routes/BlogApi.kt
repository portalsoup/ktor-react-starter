package com.portalsoup.ktorexposed.api.routes

import com.portalsoup.ktorexposed.api.BaseApi
import com.portalsoup.ktorexposed.core.monad.Try.Failure
import com.portalsoup.ktorexposed.core.monad.Try.Success
import com.portalsoup.ktorexposed.core.service.BlogPostService
import com.portalsoup.ktorexposed.resources.blogpost.BlogPostListResource
import com.portalsoup.ktorexposed.resources.blogpost.BlogPostResource
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

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
                    when (val maybeUer = withIdentity(call) { it }) {
                        is Success -> {
                            val newPost = call.receive<BlogPostListResource>()
                            val posts = BlogPostService.create(newPost, maybeUer.data)
                            call.respond(posts)
                        }
                        is Failure -> call.respond(HttpStatusCode.BadRequest)
                    }
                }
            }
        }
    }
}
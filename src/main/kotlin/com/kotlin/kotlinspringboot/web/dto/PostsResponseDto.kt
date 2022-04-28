package com.kotlin.kotlinspringboot.web.dto

import com.kotlin.kotlinspringboot.domain.posts.Posts

data class PostsResponseDto(val post: Posts) {
    val id: Long = post.id
    val title: String = post.title
    val author: String = post.author
    val content: String = post.content
}
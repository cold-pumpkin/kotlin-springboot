package com.kotlin.kotlinspringboot.web.dto

import com.kotlin.kotlinspringboot.domain.posts.Posts

data class PostsSaveRequestDto(
    val title: String,
    val author: String,
    val content: String
) {
    fun toEntity(): Posts = Posts(title, content, author)
}
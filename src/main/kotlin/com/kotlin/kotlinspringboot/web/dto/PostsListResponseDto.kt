package com.kotlin.kotlinspringboot.web.dto

import com.kotlin.kotlinspringboot.domain.posts.Posts
import java.time.LocalDateTime

data class PostsListResponseDto(
    val id: Long,
    val title: String,
    val author: String,
    val modifiedDate: LocalDateTime
) {
    constructor(entity: Posts) : this(entity.id, entity.title, entity.author, entity.modifiedDate ?: LocalDateTime.now())
}
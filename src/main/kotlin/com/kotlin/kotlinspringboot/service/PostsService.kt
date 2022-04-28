package com.kotlin.kotlinspringboot.service

import com.kotlin.kotlinspringboot.domain.posts.PostsRepository
import com.kotlin.kotlinspringboot.web.dto.PostsListResponseDto
import com.kotlin.kotlinspringboot.web.dto.PostsResponseDto
import com.kotlin.kotlinspringboot.web.dto.PostsSaveRequestDto
import com.kotlin.kotlinspringboot.web.dto.PostsUpdateRequestDto
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class PostsService(
    private val postsRepository: PostsRepository
) {

    @Transactional
    fun save(requestDto: PostsSaveRequestDto): Long? = postsRepository.save(requestDto.toEntity()).id

    @Transactional
    fun update(id: Long, requestDto: PostsUpdateRequestDto): Long? {
        val posts = postsRepository.findByIdOrNull(id) ?: throw IllegalArgumentException("존재하지 않은 게시글 입니다 : $id")
        posts.update(requestDto)
        return posts.id
    }

    @Transactional(readOnly = true)
    fun findAllDesc(): List<PostsListResponseDto> {
        return postsRepository.findAllDesc()
            .map { posts -> PostsListResponseDto(posts) }
    }

    @Transactional(readOnly = true)
    fun findById(id: Long): PostsResponseDto {
        val posts = postsRepository.findByIdOrNull(id) ?: throw IllegalArgumentException("존재하지 않은 게시글 입니다 : $id")
        return PostsResponseDto(posts)
    }

    @Transactional
    fun delete(id: Long): Long? {
        val posts = postsRepository.findByIdOrNull(id) ?: throw IllegalArgumentException("존재하지 않은 게시글 입니다 : $id")
        postsRepository.delete(posts)
        return posts.id
    }

}
package com.kotlin.kotlinspringboot.web.controller

import com.kotlin.kotlinspringboot.service.PostsService
import com.kotlin.kotlinspringboot.web.dto.PostsSaveRequestDto
import com.kotlin.kotlinspringboot.web.dto.PostsUpdateRequestDto
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class PostsApiController(
    private val postsService: PostsService
) {

    @PostMapping("/posts")
    fun save(@RequestBody requestDto: PostsSaveRequestDto): Long? = postsService.save(requestDto)

    @PutMapping("/posts/{id}")
    fun update(@PathVariable id: Long, @RequestBody postsUpdateRequestDto: PostsUpdateRequestDto): Long? = postsService.update(id, postsUpdateRequestDto)

    @DeleteMapping("/posts/{id}")
    fun delete(@PathVariable id: Long): Long? = postsService.delete(id)

}
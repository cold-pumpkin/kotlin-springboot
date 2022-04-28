package com.kotlin.kotlinspringboot.domain.posts

import com.kotlin.kotlinspringboot.domain.BaseTimeEntity
import com.kotlin.kotlinspringboot.web.dto.PostsUpdateRequestDto
import javax.persistence.*

@Entity
class Posts(
    title: String,
    content: String,
    author: String
) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @Column
    var title: String = title

    @Column
    var content: String = content

    @Column
    var author: String = author

    fun update(postsUpdateRequestDto: PostsUpdateRequestDto) {
        this.title = postsUpdateRequestDto.title
        this.content = postsUpdateRequestDto.content
    }

}
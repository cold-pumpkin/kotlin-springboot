package com.kotlin.kotlinspringboot.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.kotlin.kotlinspringboot.domain.posts.Posts
import com.kotlin.kotlinspringboot.domain.posts.PostsRepository
import com.kotlin.kotlinspringboot.web.dto.PostsSaveRequestDto
import com.kotlin.kotlinspringboot.web.dto.PostsUpdateRequestDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@AutoConfigureMockMvc
@WithMockUser(roles = ["USER"])
internal class PostsApiControllerTest(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
    private val postsRepository: PostsRepository
) {

    @AfterEach
    fun clear() = postsRepository.deleteAll()

    @Test
    @DisplayName("Posts가 등록된다")
    fun registerPostTest() {
        // given
        val postTitle = "Kotlin & Spring Boot"
        val postContent = "How to build Web App with kotlin & spring boot"

        val requestDto = PostsSaveRequestDto(postTitle,"philip", postContent)

        // when
        val responseEntity = mockMvc.post("/api/v1/posts") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(requestDto)
        }.andDo { print() }
        val posts = postsRepository.findAll()

        // then
        responseEntity.andExpect {
            status { isOk() }
            ResultMatcher { it.response.contentAsString.toLong() > 0 }
        }

        assertThat(posts).hasSize(1)

        println(posts[0].content)
        println(posts[0].title)
        println(posts[0].author)

        assertThat(posts[0].content).isEqualTo(postContent)
        assertThat(posts[0].title).isEqualTo(postTitle)
    }

    @Test
    @DisplayName("Posts가 수정된다")
    fun updatePostTest() {
        //given
        val title = "title"
        val postContent = "content"
        val updateTitle = "update title"
        val updateContent = "content updated"
        val savedPost = postsRepository.save(Posts(title = title, content = postContent, author = "author"))

        val requestDto = PostsUpdateRequestDto(title = updateTitle, content = updateContent)

        //when
        val resultActionsDsl = mockMvc.put("/api/v1/posts/${savedPost.id}") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(requestDto)
        }.andDo { print() }
        val posts = postsRepository.findAll()

        //then
        resultActionsDsl.andExpect {
            status { isOk() }
            ResultMatcher { result -> assertThat(result.response.contentAsString.toLongOrNull()).isEqualTo(savedPost.id) }
        }
        assertThat(posts).hasSize(1)
        assertThat(posts[0].content).isEqualTo(updateContent)
        assertThat(posts[0].title).isEqualTo(updateTitle)
    }

}
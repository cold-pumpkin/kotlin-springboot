package com.kotlin.kotlinspringboot.web.controller

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.MediaType
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@AutoConfigureMockMvc
internal class ProfileControllerTest (
    private val mockMvc: MockMvc
) {
    @Test
    @DisplayName("profile은 인증없이 호출된다")
    fun testCallProfile() {
        val expected = "default"
        mockMvc.get("/profile")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                ResultMatcher {
                    it.response.contentAsString.contains(expected)
                }
            }
    }




}
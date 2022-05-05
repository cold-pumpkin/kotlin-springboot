package com.kotlin.kotlinspringboot.web.controller

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.mock.env.MockEnvironment

class ProfileControllerUnitTest {

    @Test
    @DisplayName("real profile이 조회된다")
    fun testSelectRealProfile() {
        // given
        val expectedProfile = "real"
        val env = MockEnvironment()
        env.addActiveProfile(expectedProfile)
        env.addActiveProfile("oauth")
        env.addActiveProfile("real-db")

        val controller = ProfileController(env)

        // when
        val profile = controller.profile()

        // then
        assertThat(profile).isEqualTo(expectedProfile)
    }

    @Test
    @DisplayName("real profile이 없으면 첫번째가 조회된다")
    fun testFirstProfile() {
        // given
        val expectedProfile = "oauth"
        val env = MockEnvironment()
        env.addActiveProfile(expectedProfile)
        env.addActiveProfile("real-db")

        val controller = ProfileController(env) // mock을 생성자에 넣어줌

        // when
        val profile = controller.profile()

        // then
        assertThat(profile).isEqualTo(expectedProfile)
    }

    @Test
    @DisplayName("active profile이 없으면 default가 조회된다")
    fun testDefaultProfile() {
        // given
        val expectedProfile = "default"
        val env = MockEnvironment()
        val controller = ProfileController(env)

        // when
        val profile = controller.profile()

        // then
        assertThat(profile).isEqualTo(expectedProfile)
    }
}
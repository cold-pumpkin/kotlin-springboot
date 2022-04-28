package com.kotlin.kotlinspringboot.config

import com.kotlin.kotlinspringboot.config.auth.LoginUserArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val loginUserArgumentResolver: LoginUserArgumentResolver
) : WebMvcConfigurer {

    // LoginUserArgumentResolver 인식될 수 있도록 추가
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(loginUserArgumentResolver)
    }
}
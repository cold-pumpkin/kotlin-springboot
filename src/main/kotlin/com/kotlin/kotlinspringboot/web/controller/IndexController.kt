package com.kotlin.kotlinspringboot.web.controller

import com.kotlin.kotlinspringboot.config.auth.LoginUser
import com.kotlin.kotlinspringboot.config.auth.dto.SessionUser
import com.kotlin.kotlinspringboot.domain.user.User
import com.kotlin.kotlinspringboot.service.PostsService
import com.kotlin.kotlinspringboot.web.dto.PostsResponseDto
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import javax.servlet.http.HttpSession

@Controller
class IndexController(
    private val postsService: PostsService,
    private val httpSession: HttpSession
) {

    @GetMapping("/")
    fun index(model: Model, @LoginUser user: SessionUser?): String {
        model.addAttribute("posts", postsService.findAllDesc())
        //val user = httpSession.getAttribute("user") as? SessionUser  // 반복적인 로직 불필요
        user?.let {
            model.addAttribute("userName", user.name)
        }
        return "index"
    }

    @GetMapping("/posts/save")
    fun savePosts(): String = "posts-save"

    @GetMapping("/posts/update/{id}")
    fun updatePosts(@PathVariable id: Long, model: Model): String {
        val postDto = postsService.findById(id)
        model.addAttribute("post", postDto)
        return "posts-update"
    }

}
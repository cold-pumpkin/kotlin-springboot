package com.kotlin.kotlinspringboot.web.controller

import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping

@RestController
class ProfileController(
    private val env: Environment? = null
) {
    @GetMapping("/profile")
    fun profile(): String {
        val profiles = listOf(*env!!.activeProfiles)
        val realProfiles = listOf("real", "real1", "real2")
        val defaultProfile = if (profiles.isEmpty()) "default" else profiles[0]
        return profiles.stream()
            .filter { o: String -> realProfiles.contains(o) }
            .findAny()
            .orElse(defaultProfile)
    }
}
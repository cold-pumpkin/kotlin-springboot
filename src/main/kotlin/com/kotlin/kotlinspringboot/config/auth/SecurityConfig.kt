package com.kotlin.kotlinspringboot.config.auth

import com.kotlin.kotlinspringboot.domain.user.Role
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

//@Configuration
@EnableWebSecurity
class SecurityConfig (private val customOAuth2UserService: CustomOAuth2UserService): WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity?) {
        http?.let { httpSecurity ->
            httpSecurity
                .csrf().disable()
                .headers().frameOptions().disable() // h2-console 화면을 사용하기 위해 해당 옵션들을 disable
                .and()
                    .authorizeRequests()    // URL별 권한 관리를 설정하는 옵션의 시작점
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile")
                        .permitAll()  // 전체 열람 권한
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name)
                    .anyRequest().authenticated()   // 나머지 URL은 모두 인증된(로그인한) 사용자
                .and()
                    .logout()
                    .logoutSuccessUrl("/")  // 로그라웃 관련 기능 설정들의 진입점
                .and()
                    .oauth2Login()  // OAuth2 로그인 기능에 대한 설정들의 진입점
                    .userInfoEndpoint() // 로그인 성공 후 사용자 정보 가져올 때의 설정들
                    .userService(customOAuth2UserService)   // 소셜 로그인 성공 시 후속 조치 진행할 UserService 인터페이스 구현체 등록
        }
    }
}
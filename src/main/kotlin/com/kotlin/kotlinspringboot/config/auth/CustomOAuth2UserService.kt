package com.kotlin.kotlinspringboot.config.auth

import com.kotlin.kotlinspringboot.config.auth.dto.OAuthAttributes
import com.kotlin.kotlinspringboot.config.auth.dto.SessionUser
import com.kotlin.kotlinspringboot.domain.user.User
import com.kotlin.kotlinspringboot.domain.user.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import java.util.*
import javax.servlet.http.HttpSession

@Service
class CustomOAuth2UserService(      // 로그인 이후 가져온 사용자 정보를 기반으로 가입/수정/세션 저장 등
    private val userRepository: UserRepository,
    private val httpSession: HttpSession
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        val delegate = DefaultOAuth2UserService()
        val oAuth2User = delegate.loadUser(userRequest)

        val registrationId = userRequest?.clientRegistration?.registrationId ?: ""  // 1) 현재 로그인 중인 서비스 구분 코드
        val userNameAttributeName = userRequest?.clientRegistration?.providerDetails?.userInfoEndpoint?.userNameAttributeName ?: ""   // 2) OAuth2 로그인 진행 시 키가 되는 필드값 (Google: "sub")

        val attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.attributes)  // 3) OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스

        val user = saveOrUpdate(attributes)
        httpSession.setAttribute("user", SessionUser(user)) // 4) 세션에 사용자 정보를 저장하기 위한 DTO 클래스

        return DefaultOAuth2User(
            Collections.singleton(SimpleGrantedAuthority(user.getRoleKey())),
            attributes.attributes,
            attributes.nameAttributeKey
        )
    }

    // 사용자 정보가 업데이트 되었을 경우 대비
    private fun saveOrUpdate(attributes: OAuthAttributes): User {

        userRepository.findByEmail(attributes.email)?.let { user ->
            user.update(name = attributes.name, picture = attributes.picture)
            return user
        }

        return userRepository.save(attributes.toEntity())
    }

}
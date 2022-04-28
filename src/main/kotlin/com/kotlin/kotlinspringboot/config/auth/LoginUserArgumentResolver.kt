package com.kotlin.kotlinspringboot.config.auth

import com.kotlin.kotlinspringboot.config.auth.dto.SessionUser
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import javax.servlet.http.HttpSession

@Component
class LoginUserArgumentResolver(
    private val httpSession: HttpSession
) : HandlerMethodArgumentResolver {

    // 컨트롤러 메서드의 특정 파라미터를 지원하는지 판단
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        val isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser::class.java) != null // 파라미터에 @LoginUser 붙어있는지
        val isUserClass = SessionUser::class.java == parameter.parameterType    // 파라미터 클래스 타입이 SessionUser 인지
        return isLoginUserAnnotation && isUserClass
    }

    // 파라미터에 전달할 객체 생성
    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        return httpSession.getAttribute("user")
    }
}
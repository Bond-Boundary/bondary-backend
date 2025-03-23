package com.bondary.support.auth

import com.bondary.support.exception.CoreApiException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class RegisterTokenArgumentResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(RegisterToken::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val request = webRequest.getNativeRequest(HttpServletRequest::class.java)
        val token = request?.getHeader(AuthProperties.AUTHORIZATION_HEADER.value)
            ?: throw CoreApiException.ServerError("토큰이 필요합니다")
            
        if (!token.startsWith(AuthProperties.BEARER.value)) {
            throw CoreApiException.ServerError("유효하지 않은 토큰 형식입니다")
        }
        
        return token.substring(AuthProperties.BEARER.value.length)
    }
}
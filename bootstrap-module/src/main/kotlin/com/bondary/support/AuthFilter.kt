package com.bondary.support

import com.bondary.application.member.`in`.TokenUseCase
import io.dodn.springboot.core.support.error.CoreApiException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class AuthFilter(
    private val tokenUseCase: TokenUseCase
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        //
        /**
         * 인증이 필요 없는 Path 는 바로 통과
         */
        if (isPublicPath(request)) {
            filterChain.doFilter(request, response)
            return
        }

        try {
            val token = request.getHeader(AuthProperties.AUTHORIZATION_HEADER.value)
            if (token != null && token.startsWith(AuthProperties.BEARER.value)) {
                val accessToken = token.substring(AuthProperties.BEARER.value.length)
                val resolved = tokenUseCase.resolveAccessToken(accessToken)

                when (resolved) {
                    is TokenUseCase.Response.Success -> {
                        AuthSecurityContextHolder.setContext(
                            AuthSecurityContext(
                                AuthMemberAuthentication(
                                    memberId = resolved.memberId
                                )
                            )
                        )
                    }
                }
            }
            filterChain.doFilter(request, response)
        } catch (e: Exception) {
            throw CoreApiException.ServerError("토큰 검증에 실패 하였습니다.")
        } finally {
            AuthSecurityContextHolder.clearContext()
        }
    }

    /**
     * 인증이 필요 없는 Open Path 인지 확인용 method
     */
    private fun isPublicPath(request: HttpServletRequest): Boolean {
        val path = request.servletPath
        return path.startsWith("/oauth") ||
                path.startsWith("/v1/login") ||
                (path.startsWith("/v1/members") && request.method == "POST")
    }
}
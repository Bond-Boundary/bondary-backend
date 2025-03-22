package com.bondary.controller

import com.bondary.application.member.`in`.OAuthLoginUseCase
import com.bondary.controller.v1.request.OAuthLoginRequest
import com.bondary.controller.v1.response.OAuthLoginResponse
import io.dodn.springboot.core.support.response.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class LoginController(
    private val oAuthLoginUseCase: OAuthLoginUseCase
) {
    @GetMapping("/oauth/kakao/callback")
    fun kakaoCallback(
        @RequestParam code: String
    ): ResponseEntity<Map<String, String>> {
        return ResponseEntity.ok(mapOf(
            "message" to "인증 코드가 성공적으로 수신되었습니다.",
            "code" to code
        ))
    }

    @PostMapping("/v1/login/{provider}")
    suspend fun oauthLogin(
        @PathVariable provider: String,
        @RequestBody request: OAuthLoginRequest
    ) : ApiResponse<OAuthLoginResponse>{
        val command = OAuthLoginUseCase.Command(
            provider = provider,
            authorizationCode = request.authorizationCode,
            redirectUri = request.redirectUri
        )

        return when(val response = oAuthLoginUseCase.login(command)) {
            is OAuthLoginUseCase.Response.Success ->
                ApiResponse.success(OAuthLoginResponse.LoginSuccess.of(response))
            is OAuthLoginUseCase.Response.NonRegistered ->
                ApiResponse.success(OAuthLoginResponse.RequireRegister.of(response))
        }
    }

}
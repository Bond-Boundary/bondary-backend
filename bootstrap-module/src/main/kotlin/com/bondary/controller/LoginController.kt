package com.bondary.controller

import com.bondary.application.member.`in`.OAuthLoginUseCase
import com.bondary.controller.v1.request.OAuthLoginRequest
import com.bondary.controller.v1.response.OAuthLoginResponse
import io.dodn.springboot.core.support.response.ApiResponse
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController(
    private val oAuthLoginUseCase: OAuthLoginUseCase
) {
    @PostMapping("/login/{provider}")
    fun oauthLogin(
        @PathVariable provider: String,
        @RequestBody request: OAuthLoginRequest
    ) : ApiResponse<OAuthLoginResponse>{
        val command = OAuthLoginUseCase.Command(
            provider = provider,
            accessToken = request.accessToken
        )

        return when(val response = oAuthLoginUseCase.login(command)) {
            is OAuthLoginUseCase.Response.Success ->
                ApiResponse.success(OAuthLoginResponse.LoginSuccess(
                    response.accessToken,
                    response.refreshToken,
                    response.isOnboarding
                ))
            is OAuthLoginUseCase.Response.NonRegistered ->
                ApiResponse.success(OAuthLoginResponse.RequireRegister(
                    response.registerToken
                ))
        }
    }

}
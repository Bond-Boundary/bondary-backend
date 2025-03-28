package com.bondary.controller.v1.response

import com.bondary.application.member.`in`.OAuthLoginUseCase

sealed class OAuthLoginResponse {
    data class LoginSuccess(
        val accessToken: String,
        val refreshToken: String,
        val isOnboarding: Boolean
    ) : OAuthLoginResponse() {
        companion object {
            fun of(response: OAuthLoginUseCase.Response.Success): OAuthLoginResponse =
                LoginSuccess(
                    accessToken = response.accessToken,
                    refreshToken = response.refreshToken,
                    isOnboarding = response.isOnboarding
                )
        }
    }

    data class RequireRegister(
        val registerToken: String
    ) : OAuthLoginResponse() {
        companion object {
            fun of(response: OAuthLoginUseCase.Response.NonRegistered): OAuthLoginResponse =
                RequireRegister(
                    registerToken = response.registerToken
                )
        }
    }
}


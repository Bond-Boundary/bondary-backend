package com.bondary.controller.v1.response

sealed class OAuthLoginResponse {
    data class LoginSuccess(
        val accessToken: String,
        val refreshToken: String,
        val isOnboarding: Boolean
    ) : OAuthLoginResponse()

    data class RequireRegister(
        val registerToken: String
    ) : OAuthLoginResponse()
}


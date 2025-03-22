package com.bondary.clients

import reactor.core.publisher.Mono

interface OAuthManager {
    fun getAccessToken(request: AuthorizationCodeRequest): Mono<AccessTokenResponse>

    fun getOAuthInfo(request: OAuthClientRequest): Mono<OAuthClientResponse>

    data class OAuthClientRequest(val accessToken: String)

    data class AuthorizationCodeRequest(
        val authorizationCode: String,
        val redirectUri: String
    )

    data class AccessTokenResponse(
        val accessToken: String,
        val refreshToken: String?,
        val expiresIn: Long
    )

    data class OAuthClientResponse(
        val name: String,
        val email: String,
        val profileImage: String,
        val socialId: String
    )

}
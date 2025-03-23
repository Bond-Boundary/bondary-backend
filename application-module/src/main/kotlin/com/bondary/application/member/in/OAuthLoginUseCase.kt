package com.bondary.application.member.`in`

interface OAuthLoginUseCase {

    suspend fun login(command: Command): Response

    data class Command(
        val provider: String,
        val authorizationCode: String,
        val redirectUri: String
    )

    sealed class Response {
        data class Success(
            val accessToken: String,
            val refreshToken: String,
            val isOnboarding: Boolean
        ) : Response()

        data class NonRegistered(
            val registerToken: String
        ) : Response()
    }
}
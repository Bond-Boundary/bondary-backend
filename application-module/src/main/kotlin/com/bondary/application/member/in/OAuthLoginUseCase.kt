package com.bondary.application.member.`in`

interface OAuthLoginUseCase {

    fun login(command: Command): Response

    data class Command(
        val provider: String,
        val accessToken: String,
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
package com.bondary.application.member.`in`

interface TokenUseCase {
    fun resolveAccessToken(token: String): Response

    fun logout(command: Response.Command)

    sealed class Response {
        data class Success(
            val memberId: String
        ) : Response()

        data class Command(
            val refreshToken: String,
            val memberId: String,
        )
    }
}
package com.bondary.application.member.`in`

interface AppendMemberUseCase {

    suspend fun appendMember(command: Command): Response.Success

    data class Command(
        val token: String,
        val introduction: String,
        val schoolName: String,
        val firstMajorName: String,
        val secondaryMajorName: String?,
        val interestArea: List<String>,
        val interestJob: String?,
        val instagram: String?,
        val linkedin: String?,
        val etcLinks: List<String>?,
    )

    sealed class Response {
        data class Success(
            val accessToken: String,
            val refreshToken: String,
        ) : Response()
    }
}
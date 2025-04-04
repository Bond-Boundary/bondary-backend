package com.bondary.application.career.`in`


interface DeleteCareerUseCase {
    fun deleteCareer(command: Command): Response.Success

    data class Command(
        val memberId: String,
        val careerId: String
    )

    sealed class Response {
        data class Success(
            val successId: String
        ) : Response()
    }
}
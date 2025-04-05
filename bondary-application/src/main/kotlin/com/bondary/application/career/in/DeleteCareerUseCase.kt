package com.bondary.application.career.`in`

import com.bondary.HasSuccessId


interface DeleteCareerUseCase {
    fun deleteCareer(command: Command): Response.Success

    data class Command(
        val memberId: String,
        val careerId: String
    )

    sealed class Response {
        data class Success(
            override val successId: String
        ) : Response(), HasSuccessId
    }
}
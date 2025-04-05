package com.bondary.application.career.`in`

import com.bondary.HasSuccessId

interface MarkingCareerUseCase {
    fun markingCareer(command: Command): Response.Success

    /**
     * 이미 대표 경력일 때도 고려해야 함
     */
    data class Command(
        val memberId: String,
        val careerId: String,
        val isRepresent: Boolean = true
    )

    sealed class Response {
        data class Success(
            override val successId: String
        ) : Response(), HasSuccessId
    }
}
package com.bondary.application.career.`in`

import java.time.LocalDateTime

interface CreateCareerUseCase {
    fun createCareer(command: Command): Response.Success

    data class Command(
        val memberId: String,
        val thumbnailImage: String,
        val title: String,
        val content: String,
        val careerStart: LocalDateTime,
        val careerEnd: LocalDateTime?,
        val isProgress: Boolean,
        val isRepresent: Boolean
    )

    sealed class Response {
        data class Success(
            val successId: String
        ) : Response()
    }
}
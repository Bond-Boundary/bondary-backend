package com.bondary.application.career.`in`

import com.bondary.HasSuccessId
import java.time.LocalDateTime

interface ModifyCareerUseCase {
    fun modifyCareer(command: Command): Response.Success

    data class Command(
        val memberId: String,
        val careerId: String,
        val thumbnailImage: String?,
        val title: String?,
        val content: String?,
        val careerStart: LocalDateTime?,
        val careerEnd: LocalDateTime?,
        val isProgress: Boolean?,
        val isRepresent: Boolean?
    )

    sealed class Response {
        data class Success(
            override val successId: String
        ) : Response(), HasSuccessId
    }
}
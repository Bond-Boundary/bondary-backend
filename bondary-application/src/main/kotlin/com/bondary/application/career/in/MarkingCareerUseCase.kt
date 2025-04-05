package com.bondary.application.career.`in`

import com.bondary.HasSuccessId

interface MarkingCareerUseCase {
    fun executeFor(command: Command.Marking): Response.Success

    fun executeFor(command: Command.UnMarking): Response.Success

    /**
     * 이미 대표 경력일 때도 고려해야 함
     */
    sealed class Command {
        abstract val memberId: String
        abstract val careerId: String
        abstract val isRepresent: Boolean

        data class Marking(
            override val memberId: String,
            override val careerId: String,
            override val isRepresent: Boolean = true
        ) : Command()

        data class UnMarking(
            override val memberId: String,
            override val careerId: String,
            override val isRepresent: Boolean = false
        ) : Command()
    }

    sealed class Response {
        data class Success(
            override val successId: String
        ) : Response(), HasSuccessId
    }
}
package com.bondary.application.career

import com.bondary.application.career.`in`.MarkingCareerUseCase
import com.bondary.application.career.out.CareerFunctionPort
import com.bondary.application.career.out.CareerMarkingFunctionPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CareerMarkingCommandService(
    private val careerMarkingFunctionPort: CareerMarkingFunctionPort,
    private val careerFunctionPort: CareerFunctionPort
) : MarkingCareerUseCase {
    @Transactional
    override fun executeFor(command: MarkingCareerUseCase.Command.Marking): MarkingCareerUseCase.Response.Success {
        val successId = executeMarkingFor(command)
        return MarkingCareerUseCase.Response.Success(successId)
    }

    @Transactional
    override fun executeFor(command: MarkingCareerUseCase.Command.UnMarking): MarkingCareerUseCase.Response.Success {
        val successId = executeMarkingFor(command)
        return MarkingCareerUseCase.Response.Success(successId)
    }

    private fun executeMarkingFor(command: MarkingCareerUseCase.Command): String {
        val career = careerFunctionPort.getCareer(command.careerId, command.memberId)
        career.markingAsRepresent(command.isRepresent)

        val successId = careerMarkingFunctionPort.executeMarkingRepresent(career)
        return successId
    }
}
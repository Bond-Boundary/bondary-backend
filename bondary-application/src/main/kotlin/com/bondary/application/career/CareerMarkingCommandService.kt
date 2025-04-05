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
    override fun markingCareer(command: MarkingCareerUseCase.Command): MarkingCareerUseCase.Response.Success {
        val career = careerFunctionPort.getCareer(command.careerId, command.memberId)
        career.markingAsRepresent(command.isRepresent)

        val successId = careerMarkingFunctionPort.marking(career)
        return MarkingCareerUseCase.Response.Success(successId)
    }
}
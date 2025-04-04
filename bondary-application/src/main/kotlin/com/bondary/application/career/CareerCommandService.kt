package com.bondary.application.career

import com.bondary.application.career.`in`.CreateCareerUseCase
import com.bondary.application.career.`in`.DeleteCareerUseCase
import com.bondary.application.career.`in`.ModifyCareerUseCase
import com.bondary.application.career.out.CareerFunctionPort
import com.bondary.career.Career
import com.bondary.support.DomainId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CareerCommandService(
    private val careerFunctionPort: CareerFunctionPort
) : CreateCareerUseCase,
    ModifyCareerUseCase,
    DeleteCareerUseCase {
    @Transactional
    override fun createCareer(command: CreateCareerUseCase.Command): CreateCareerUseCase.Response.Success {
        val create = Career.createCareer(
            memberId = DomainId(command.memberId),
            title = command.title,
            content = command.content,
            thumbnailImage = command.thumbnailImage,
            careerStart = command.careerStart,
            careerEnd = command.careerEnd,
            isProgress = command.isProgress,
            isRepresent = command.isRepresent
        )

        val successId = careerFunctionPort.save(create)
        return CreateCareerUseCase.Response.Success(successId)
    }

    @Transactional
    override fun modifyCareer(command: ModifyCareerUseCase.Command): ModifyCareerUseCase.Response.Success {
        val career = careerFunctionPort.getCareer(command.careerId, command.memberId)
        career.modifyCareer(
            title = command.title,
            content = command.content,
            thumbnailImage = command.thumbnailImage,
            careerStart = command.careerStart,
            careerEnd = command.careerEnd,
            isProgress = command.isProgress,
            isRepresent = command.isRepresent
        )

        val successId = careerFunctionPort.modify(career)
        return ModifyCareerUseCase.Response.Success(successId)
    }

    @Transactional
    override fun deleteCareer(command: DeleteCareerUseCase.Command): DeleteCareerUseCase.Response.Success {
        val career = careerFunctionPort.getCareer(command.careerId, command.memberId)
            .also { it.deleteCareer() }

        val successId = careerFunctionPort.delete(career)
        return DeleteCareerUseCase.Response.Success(successId)
    }
}
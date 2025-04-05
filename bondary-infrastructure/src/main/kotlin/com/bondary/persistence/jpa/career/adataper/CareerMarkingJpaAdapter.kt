package com.bondary.persistence.jpa.career.adataper

import com.bondary.application.career.out.CareerMarkingFunctionPort
import com.bondary.career.Career
import com.bondary.persistence.jpa.career.repository.CareerJpaRepository
import org.springframework.stereotype.Repository

@Repository
class CareerMarkingJpaAdapter(
    private val careerJpaRepository: CareerJpaRepository
) : CareerMarkingFunctionPort {
    override fun updateMarkingRepresent(career: Career): String {
        careerJpaRepository.updateMarkingRepresentByIdAndMemberId(
            id = career.id.value,
            memberId = career.memberId.value,
            isRepresent = career.isRepresent
        )
        return career.id.value
    }
}
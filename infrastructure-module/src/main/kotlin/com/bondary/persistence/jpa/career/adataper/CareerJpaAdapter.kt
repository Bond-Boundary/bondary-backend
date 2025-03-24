package com.bondary.persistence.jpa.career.adataper

import com.bondary.application.career.out.CareerFunctionPort
import com.bondary.career.Career
import com.bondary.persistence.jpa.career.mapper.CareerMapper
import com.bondary.persistence.jpa.career.repository.CareerJpaRepository
import org.springframework.stereotype.Repository

@Repository
class CareerJpaAdapter(
    private val careerJpaRepository: CareerJpaRepository
) : CareerFunctionPort{
    override fun save(career: Career): String {
        val careerEntity = CareerMapper.toCareerEntity(career)
        val save = careerJpaRepository.save(careerEntity)
        return save.id
    }
}
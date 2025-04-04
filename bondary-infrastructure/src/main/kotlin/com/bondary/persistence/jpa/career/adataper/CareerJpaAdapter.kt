package com.bondary.persistence.jpa.career.adataper

import com.bondary.application.career.out.CareerFunctionPort
import com.bondary.career.Career
import com.bondary.career.exception.CareerException
import com.bondary.persistence.jpa.career.mapper.CareerMapper
import com.bondary.persistence.jpa.career.repository.CareerJpaRepository
import com.bondary.persistence.jpa.career.repository.deleteByMemberIdAndId
import com.bondary.persistence.jpa.support.EntityStatus
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class CareerJpaAdapter(
    private val careerJpaRepository: CareerJpaRepository
) : CareerFunctionPort {
    override fun save(career: Career): String {
        val careerEntity = CareerMapper.toCareerEntity(career)
        val save = careerJpaRepository.save(careerEntity)
        return save.id
    }

    override fun modify(career: Career): String {
        val modify = careerJpaRepository.findByIdOrNull(career.id.value)
            ?.let {
                it.modify(career)
                careerJpaRepository.save(it) }
            ?: throw CareerException.CareerNotFound()
        return modify.id
    }

    override fun delete(career: Career): String {
        careerJpaRepository.deleteByMemberIdAndId(
            memberId = career.memberId.value,
            id = career.id.value,
            entityStatus = EntityStatus.valueOf(career.domainStatus.toString())
        )
        return career.id.value
    }

    override fun getCareer(careerId: String, memberId: String): Career =
        careerJpaRepository.findByIdOrNull(careerId)
            ?.let { CareerMapper.toCareerDomain(it) }
            ?: throw CareerException.CareerNotFound()
}
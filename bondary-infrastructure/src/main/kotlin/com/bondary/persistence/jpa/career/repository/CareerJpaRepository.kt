package com.bondary.persistence.jpa.career.repository

import com.bondary.persistence.jpa.career.entity.CareerEntity
import com.bondary.persistence.jpa.support.EntityStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface CareerJpaRepository : JpaRepository<CareerEntity, String> {
    @Modifying
    @Query(
        """
        UPDATE CareerEntity c
        SET c.entityStatus = :entityStatus
        WHERE c.id = :id
        AND c.memberId = :memberId
    """
    )
    fun updateCareerEntityStatusByIdAndMemberId(
        id: String,
        memberId: String,
        entityStatus: EntityStatus
    )
}

fun CareerJpaRepository.deleteByMemberIdAndId(
    memberId: String,
    id: String,
    entityStatus: EntityStatus
) {
    updateCareerEntityStatusByIdAndMemberId(id, memberId, entityStatus)
}
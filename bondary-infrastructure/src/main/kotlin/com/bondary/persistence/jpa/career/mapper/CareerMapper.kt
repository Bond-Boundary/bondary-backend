package com.bondary.persistence.jpa.career.mapper

import com.bondary.career.Career
import com.bondary.career.exception.CareerException
import com.bondary.career.values.CareerDetails
import com.bondary.career.values.CareerPeriod
import com.bondary.persistence.jpa.career.entity.CareerEntity
import com.bondary.support.DomainId

object CareerMapper {
    fun toCareerEntity(career: Career) : CareerEntity =
        CareerEntity(
            id = career.id.value,
            memberId = career.memberId.value,
            thumbnailImage = career.careerDetails.thumbnailImage,
            title = career.careerDetails.title,
            content = career.careerDetails.content,
            careerStart = career.careerPeriod.careerStart,
            careerEnd = career.careerPeriod.careerEnd,
            isProgress = career.careerPeriod.isProgress,
            isRepresent = career.isRepresent
        )

    fun toCareerDomain(careerEntity: CareerEntity): Career =
        Career(
            id = DomainId(careerEntity.id),
            memberId = DomainId(careerEntity.memberId),
            careerDetails = CareerDetails(careerEntity.title, careerEntity.content, careerEntity.thumbnailImage),
            careerPeriod = if (careerEntity.isProgress) {
                CareerPeriod.createInProgress(careerEntity.careerStart) }
            else {
                careerEntity.careerEnd?.let {
                    CareerPeriod.createCompleted(careerEntity.careerStart, it)
                } ?: throw CareerException.EndDateRequired()
            },
            isRepresent = careerEntity.isRepresent,
            createdAt = careerEntity.createdAt,
            updatedAt = careerEntity.updatedAt
        )
}
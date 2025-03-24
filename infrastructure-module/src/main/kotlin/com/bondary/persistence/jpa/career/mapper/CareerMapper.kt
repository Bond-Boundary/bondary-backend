package com.bondary.persistence.jpa.career.mapper

import com.bondary.career.Career
import com.bondary.persistence.jpa.career.entity.CareerEntity

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
}
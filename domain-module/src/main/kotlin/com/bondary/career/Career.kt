package com.bondary.career

import com.bondary.career.exception.CareerException
import com.bondary.career.values.CareerDetails
import com.bondary.career.values.CareerPeriod
import com.bondary.support.AggregateDomain
import com.bondary.support.DomainId
import java.time.LocalDateTime

class Career private constructor(
    id: DomainId,
    val memberId: DomainId,
    var careerDetails: CareerDetails,
    var careerPeriod: CareerPeriod,
    var isRepresent: Boolean = false,
    createdAt: LocalDateTime,
    updatedAt: LocalDateTime,
) : AggregateDomain<Career>(id, createdAt, updatedAt) {
    companion object {
        fun createCareer(
            id: DomainId = DomainId.generate(),
            memberId: DomainId,
            title: String,
            content: String,
            thumbnailImage: String,
            careerStart: LocalDateTime,
            careerEnd: LocalDateTime? = null,
            isProgress: Boolean,
            isRepresent: Boolean,
            createdAt: LocalDateTime = LocalDateTime.now(),
            updatedAt: LocalDateTime = LocalDateTime.now()
        ): Career {
            val details = CareerDetails.createCareerDetails(
                title = title,
                content = content,
                thumbnailImage = thumbnailImage
            )

            val period = when {
                isProgress -> CareerPeriod.createInProgress(careerStart)
                careerEnd != null -> CareerPeriod.createCompleted(careerStart, careerEnd)
                else -> throw CareerException.EndDateRequired()
            }

            return Career(
                id = id,
                memberId = memberId,
                careerDetails = details,
                careerPeriod = period,
                isRepresent = isRepresent,
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        }
    }

    fun updateDetails(newDetails: CareerDetails) {
        this.careerDetails = newDetails
    }

    fun updatePeriods(newPeriod: CareerPeriod) {
        this.careerPeriod = newPeriod
    }

    fun markAsRepresent() {
        if (this.isRepresent) return
        this.isRepresent = true
    }

    fun unmarkAsRepresent() {
        if (!this.isRepresent) return
        this.isRepresent = false
    }

    fun getDuration(): Long = careerPeriod.duration()

    fun isActive(): Boolean {
        val endDate = careerPeriod.careerEnd
        return careerPeriod.isProgress || (endDate != null && endDate.isAfter(LocalDateTime.now()))
    }
}
package com.bondary.career.values

import com.bondary.career.exception.CareerException
import java.time.LocalDateTime

data class CareerPeriod(
    val careerStart: LocalDateTime,
    val careerEnd: LocalDateTime?,
    val isProgress: Boolean
){
    init {
        if (!isProgress) {
            requireNotNull(careerEnd) { CareerException.EndDateRequired() }
            require(!careerEnd.isBefore(careerStart)) { CareerException.InvalidDateRange() }
        }
    }

    companion object {
        fun createInProgress(careerStart: LocalDateTime): CareerPeriod =
            CareerPeriod(
                careerStart = careerStart,
                careerEnd = null,
                isProgress = true
            )

        fun createCompleted(careerStart: LocalDateTime, careerEnd: LocalDateTime): CareerPeriod =
            CareerPeriod(
                careerStart = careerStart,
                careerEnd = careerEnd,
                isProgress = false
            )
    }

    fun duration(): Long {
        val end = careerEnd ?: LocalDateTime.now()
        return java.time.Duration.between(careerStart, end).toDays()
    }



}
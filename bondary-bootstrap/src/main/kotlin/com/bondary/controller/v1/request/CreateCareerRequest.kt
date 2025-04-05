package com.bondary.controller.v1.request

import com.bondary.support.validator.ValidCareerPeriod
import java.time.LocalDateTime

@ValidCareerPeriod
data class CreateCareerRequest(
    val thumbnailImage: String,
    val title: String,
    val content: String,
    val careerStart: LocalDateTime,
    val careerEnd: LocalDateTime?,
    val isProgress: Boolean,
    val isRepresent: Boolean
)

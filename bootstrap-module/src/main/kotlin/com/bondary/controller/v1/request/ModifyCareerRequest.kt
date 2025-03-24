package com.bondary.controller.v1.request

import java.time.LocalDateTime

data class ModifyCareerRequest(
    val thumbnailImage: String?,
    val title: String?,
    val content: String?,
    val careerStart: LocalDateTime?,
    val careerEnd: LocalDateTime?,
    val isProgress: Boolean?,
    val isRepresent: Boolean?
)
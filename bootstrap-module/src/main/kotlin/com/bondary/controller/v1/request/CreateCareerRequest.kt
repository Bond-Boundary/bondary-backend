package com.bondary.controller.v1.request

import com.bondary.support.exception.CoreApiException
import java.time.LocalDateTime

data class CreateCareerRequest(
    val thumbnailImage: String,
    val title: String,
    val content: String,
    val careerStart: LocalDateTime,
    val careerEnd: LocalDateTime?,
    val isProgress: Boolean,
    val isRepresent: Boolean
) {
    fun validate() {
        if (isProgress && careerEnd != null) {
            throw CoreApiException.BadRequest("진행 중인 경력은 종료 일을 입력할 수 없습니다.")
        }

        if (!isProgress && careerEnd == null) {
            throw CoreApiException.BadRequest("진행 중이지 않은 경력은 종료 일을 입력해야 합니다.")
        }
    }
}

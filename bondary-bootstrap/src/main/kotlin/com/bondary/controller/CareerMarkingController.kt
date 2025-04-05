package com.bondary.controller

import com.bondary.application.career.`in`.MarkingCareerUseCase
import com.bondary.controller.v1.response.DefaultIdResponse
import com.bondary.support.auth.AuthProvider
import com.bondary.support.exception.ApiResponse
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CareerMarkingController(
    private val markingCareerUseCase: MarkingCareerUseCase
) {
    @PutMapping("/v1/careers/{careerId}/mark")
    fun markUpRepresent(
        @AuthProvider memberId: String,
        @PathVariable careerId: String,
    ): ApiResponse<DefaultIdResponse> {
        val response = markingCareerUseCase.markingCareer(
            MarkingCareerUseCase.Command(
                memberId = memberId,
                careerId = careerId
            )
        )
        return ApiResponse.success(DefaultIdResponse.of(response))
    }
}
package com.bondary.controller

import com.bondary.application.career.`in`.CreateCareerUseCase
import com.bondary.controller.v1.request.CreateCareerRequest
import com.bondary.controller.v1.response.DefaultIdResponse
import com.bondary.support.auth.AuthProvider
import com.bondary.support.exception.ApiResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CareerController(
    private val createCareerUseCase: CreateCareerUseCase
) {
    @PostMapping("/v1/careers")
    fun createCareer(
        @AuthProvider memberId: String,
        @RequestBody request: CreateCareerRequest
    ): ApiResponse<DefaultIdResponse> {
        val response = createCareerUseCase.createCareer(
            CreateCareerUseCase.Command(
                memberId = memberId,
                thumbnailImage = request.thumbnailImage,
                title = request.title,
                content = request.content,
                careerStart = request.careerStart,
                careerEnd = request.careerEnd,
                isProgress = request.isProgress,
                isRepresent = request.isRepresent
            )
        )
        return ApiResponse.success(DefaultIdResponse.of(response))
    }

}
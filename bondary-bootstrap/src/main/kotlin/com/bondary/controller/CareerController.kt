package com.bondary.controller

import com.bondary.application.career.`in`.CreateCareerUseCase
import com.bondary.application.career.`in`.DeleteCareerUseCase
import com.bondary.application.career.`in`.ModifyCareerUseCase
import com.bondary.controller.v1.request.CreateCareerRequest
import com.bondary.controller.v1.request.ModifyCareerRequest
import com.bondary.controller.v1.response.DefaultIdResponse
import com.bondary.support.auth.AuthProvider
import com.bondary.support.exception.ApiResponse
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
class CareerController(
    private val createCareerUseCase: CreateCareerUseCase,
    private val modifyCareerUseCase: ModifyCareerUseCase,
    private val deleteCareerUseCase: DeleteCareerUseCase
) {
    @PostMapping("/v1/careers")
    fun createCareer(
        @AuthProvider memberId: String,
        @Valid @RequestBody request: CreateCareerRequest
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

    @PutMapping("v1/careers/{careerId}")
    fun modifyCareer(
        @AuthProvider memberId: String,
        @PathVariable careerId: String,
        @Valid @RequestBody request: ModifyCareerRequest
    ): ApiResponse<DefaultIdResponse> {
        val response = modifyCareerUseCase.modifyCareer(
            ModifyCareerUseCase.Command(
                memberId = memberId,
                careerId = careerId,
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

    @DeleteMapping("v1/careers/{careerId}")
    fun deleteCareer(
        @AuthProvider memberId: String,
        @PathVariable careerId: String
    ): ApiResponse<DefaultIdResponse> {
        val response = deleteCareerUseCase.deleteCareer(
            DeleteCareerUseCase.Command(
                memberId = memberId,
                careerId = careerId
            )
        )
        return ApiResponse.success(DefaultIdResponse.of(response))
    }

}
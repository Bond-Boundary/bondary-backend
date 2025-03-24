package com.bondary.controller.v1.response

import com.bondary.application.career.`in`.CreateCareerUseCase

data class DefaultIdResponse(
    val successId: String
) {
    companion object{
        fun of(response: CreateCareerUseCase.Response.Success) : DefaultIdResponse =
            DefaultIdResponse(response.successId)
    }
}
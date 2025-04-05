package com.bondary.controller.v1.response

import com.bondary.HasSuccessId

data class DefaultIdResponse(
    val successId: String
) {
    companion object{
        fun of(response: HasSuccessId) : DefaultIdResponse =
            DefaultIdResponse(response.successId)
    }
}
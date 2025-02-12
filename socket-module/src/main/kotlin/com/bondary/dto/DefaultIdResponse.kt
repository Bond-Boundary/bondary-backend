package com.bondary.dto

data class DefaultIdResponse(
    val id: String
) {
    companion object {
        fun of(successId: String): DefaultIdResponse {
            return DefaultIdResponse(successId)
        }
    }
}

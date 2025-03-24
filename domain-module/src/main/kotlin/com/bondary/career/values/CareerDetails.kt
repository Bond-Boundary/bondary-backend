package com.bondary.career.values

import com.bondary.career.exception.CareerException

data class CareerDetails(
    val title: String,
    val content: String,
    val thumbnailImage: String
) {
    init {
        require(title.isNotBlank()) { CareerException.TitleEmpty() }
        require(content.isNotBlank()) { CareerException.ContentEmpty() }
        require(content.length <= 500) { CareerException.ContentTooLong() }
    }

    companion object {
        fun createCareerDetails(
            title: String,
            content: String,
            thumbnailImage: String
        ): CareerDetails =
            CareerDetails(
                title = title.trim(),
                content = content.trim(),
                thumbnailImage = thumbnailImage
            )
    }
}
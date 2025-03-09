package com.bondary.controller.v1.liveSearch.res

import com.bondary.model.User

data class SearchResponse(
    val id: String,
    val name: String,
    val thumbnail: String,
    val content: String
) {
    companion object {
        fun fromUser(user: User): SearchResponse {
            return SearchResponse(
                id = user.id,
                name = user.name,
                thumbnail = user.thumbnailUrl ?: "", // User의 필드명에 따라 조정 필요
                content = user.description ?: ""     // User의 필드명에 따라 조정 필요
            )
        }
    }
}
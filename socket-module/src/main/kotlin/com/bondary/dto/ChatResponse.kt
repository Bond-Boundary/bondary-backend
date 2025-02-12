package com.bondary.dto

import com.bondary.entity.Chat

data class ChatResponse(
    val id: String,
    val title: String,
    val thumbnailId: Long
) {
    companion object {
        fun fromEntity(chat: Chat): ChatResponse {
            return ChatResponse(
                id = chat.id ?: "unknown",
                title = chat.title,
                thumbnailId = chat.thumbnailId
            )
        }
    }
}

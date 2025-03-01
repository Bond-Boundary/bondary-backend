package com.bondary.controller.v1.response

import com.bondary.model.Chat
import java.time.Instant

data class ChatResponse(
    val id: String,
    val chatType: String,
    val participants: List<Long>,
    val lastMessage: String?,
    val createdAt: Instant,
    val updatedAt: Instant
) {
    companion object {
        fun from(chat: Chat): ChatResponse = ChatResponse(
            id = chat.id!!,
            chatType = chat.chatType.toString(),
            participants = chat.participants,
            lastMessage = chat.lastMessage,
            createdAt = chat.createdAt,
            updatedAt = chat.updatedAt
        )
    }
}

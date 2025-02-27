package com.bondary.controller.v1.response

import com.bondary.model.Message
import java.time.Instant

data class MessageResponse(
    val id: String,
    val chatId: String,
    val senderId: Long,
    val receiverId: Long,
    val content: String,
    val type: String,
    val timestamp: Instant
) {
    companion object {
        fun of(message: Message): MessageResponse {
            return MessageResponse(
                id = message.id ?: "",
                chatId = message.chatId,
                senderId = message.senderId,
                receiverId = message.receiverId,
                content = message.content,
                type = message.type.toString(),
                timestamp = message.timestamp
            )
        }
    }
}


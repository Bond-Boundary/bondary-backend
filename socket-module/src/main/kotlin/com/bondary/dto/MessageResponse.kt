package com.bondary.dto

import com.bondary.entity.Message
import java.time.LocalDateTime

data class MessageResponse(
    val id: String,
    val chatId: Long,
    val senderId: Long,
    val receiverId: Long,
    val text: String,
    val timestamp: LocalDateTime,
    val isRead: Boolean
) {
    companion object {
        fun fromEntity(message: Message): MessageResponse {
            return MessageResponse(
                id = message.id ?: "unknown",
                chatId = message.chatId,
                senderId = message.senderId,
                receiverId = message.receiverId,
                text = message.text,
                timestamp = message.timestamp,
                isRead = message.isRead
            )
        }

        fun of(messages: List<Message>): List<MessageResponse> {
            return messages.stream()
                .map { MessageResponse.fromEntity(it) }
                .toList()
        }
    }
}

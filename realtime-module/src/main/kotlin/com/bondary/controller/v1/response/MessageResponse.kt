package com.bondary.controller.v1.response

import com.bondary.model.Message
import java.time.Instant

data class MessageResponse(
    val id: String,
    val chatId: String,
    val senderId: Long,
    val receiverId: Long,
    val content: String,
    val isRead: Boolean,
    val type: String,
    val timestamp: Instant,
) {
    companion object {
        fun from(messages: List<Message>): List<MessageResponse> =
            messages.map { message ->
                MessageResponse(
                    id = message.id ?: "",
                    chatId = message.chatId,
                    senderId = message.senderId,
                    receiverId = message.receiverId,
                    content = message.content,
                    isRead = message.isRead,
                    type = message.messageType.toString(),
                    timestamp = message.createdAt,
                )
            }
    }
}
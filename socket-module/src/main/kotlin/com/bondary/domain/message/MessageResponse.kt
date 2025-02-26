package com.bondary.domain.message

import java.time.LocalDateTime

data class MessageResponse(
    val id: String,
    val chatId: String,
    val senderId: Long,
    val messageType: String,
    val content: String,
    val timestamp: LocalDateTime,
    val isRead: Boolean
) {
    companion object {
        fun of(message: Message): MessageResponse {
            return MessageResponse(
                id = message.id ?: "",
                chatId = message.chatId,
                senderId = message.senderId,
                messageType = message.messageType.name,
                content =
                    when (val payload = message.payload) {
                        is MessagePayload.TextPayload -> payload.content
                        is MessagePayload.SystemPayload -> payload.content
                        is MessagePayload.FilePayload -> payload.fileName
                        is MessagePayload.ImagePayload -> payload.imageUrl
                        is MessagePayload.ReadPayload -> payload.content
                    },
                timestamp = message.timestamp,
                isRead = message.isRead,
            )
        }
    }
}

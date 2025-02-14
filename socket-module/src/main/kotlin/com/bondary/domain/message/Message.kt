package com.bondary.domain.message

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "messages")
data class Message(
    @Id
    val id: String? = null,
    val chatId: String,
    val senderId: String,
    val messageType: MessageType,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val isRead: Boolean = false,
    val payload: MessagePayload
)

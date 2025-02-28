package com.bondary.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "messages")
data class Message(
    @Id
    val id: String? = null,
    val chatId: String,
    val senderId: Long,
    val receiverId: Long,
    val content: String,
    var isRead: Boolean = false,
    var type: MessageType = MessageType.TEXT,
    var messageStatus: MessageStatus = MessageStatus.SENT,
    var metadata: Map<String, Any> = emptyMap(),
    val createdAt: Instant = Instant.now()
)

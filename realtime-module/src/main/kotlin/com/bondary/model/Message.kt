package com.bondary.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "messages")
data class Message(
    @Id
    val id: String? = null,
    val chatId: String,
    val senderId: String,
    val receiverId: String,
    val content: String,
    var type: MessageType = MessageType.TEXT,
    val timestamp: Instant = Instant.now()
)

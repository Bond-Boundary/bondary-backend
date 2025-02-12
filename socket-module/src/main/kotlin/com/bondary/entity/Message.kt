package com.bondary.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "messages")
data class Message(
    @Id
    val id: String? = null,
    val chatId: Long,
    val senderId: Long,
    val receiverId: Long,
    val text: String,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val isRead: Boolean = false
)

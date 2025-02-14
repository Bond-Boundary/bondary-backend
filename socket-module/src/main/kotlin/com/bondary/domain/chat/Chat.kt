package com.bondary.domain

import com.bondary.domain.chat.ChatType
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "chats")
data class Chat(
    @Id
    val id: String? = null,
    val type: ChatType,
    val lastMessage: LastMessage? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    data class LastMessage(
        val content: String,
        val senderId: Long,
        val timestamp: LocalDateTime
    )
}

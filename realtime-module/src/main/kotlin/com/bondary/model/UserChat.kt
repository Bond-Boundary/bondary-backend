package com.bondary.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "user_chat")
data class UserChat(
    @Id
    val id: String? = null,
    val userId: Long,
    val chatId: String,
    val chatTitle: String,
    val unreadCount: Int = 0,
    var isMuted: Boolean = false,
    var displayIndex: String,
    val updatedAt: Instant = Instant.now(),
)

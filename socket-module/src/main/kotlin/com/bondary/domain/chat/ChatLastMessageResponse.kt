package com.bondary.domain.chat

import java.time.LocalDateTime

data class ChatLastMessageResponse(
    val content: String,
    val senderId: String,
    val timestamp: LocalDateTime,
    val messageType: String,
    val isRead: Boolean
)

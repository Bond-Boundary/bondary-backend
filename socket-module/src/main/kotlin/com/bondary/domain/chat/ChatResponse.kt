package com.bondary.domain.chat

import java.time.LocalDateTime

data class ChatResponse(
    val chatId: Long,
    val chatType: String,
    val displayIdx: String,
    val lastMessage: ChatLastMessageResponse?,
    val updatedAt: LocalDateTime
)

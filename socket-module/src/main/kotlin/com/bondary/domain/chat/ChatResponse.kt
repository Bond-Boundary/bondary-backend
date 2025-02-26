package com.bondary.domain.chat

import java.time.LocalDateTime

data class ChatResponse(
    val chatId: String,
    val chatType: String,
    val title : String = "채팅방 임시 이름",
    val displayIdx: String,
    val lastMessage: ChatLastMessageResponse?,
    val updatedAt: LocalDateTime,
    val relationshipType: String
)

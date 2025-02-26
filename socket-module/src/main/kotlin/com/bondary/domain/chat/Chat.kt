package com.bondary.domain

import com.bondary.domain.chat.ChatType
import com.bondary.support.BaseEntity
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "chats")
data class Chat(
    @Id
    val id: String? = null,
    val chatType: ChatType,
    var lastMessage: LastMessage? = null
) : BaseEntity() {
    data class LastMessage(
        val content: String,
        val senderId: Long,
        val timestamp: LocalDateTime
    )
}

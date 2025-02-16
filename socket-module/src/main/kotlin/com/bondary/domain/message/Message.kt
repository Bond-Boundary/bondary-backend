package com.bondary.domain.message

import com.bondary.support.BaseEntity
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "messages")
data class Message(
    @Id
    val id: String? = null,
    val chatId: Long,
    val senderId: Long,
    val messageType: MessageType,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    var isRead: Boolean = false,
    val payload: MessagePayload
) : BaseEntity()

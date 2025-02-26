package com.bondary.domain.message

import com.bondary.support.BaseEntity
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.CompoundIndexes
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "messages")
@CompoundIndexes(
    CompoundIndex(
        name = "chat_sender_read_idx",
        def = "{'chatId': 1, 'senderId': 1, 'isRead': 1}",
    ),
    CompoundIndex(
        name = "chat_timestamp_idx",
        def = "{'chatId': 1, 'timestamp': -1}",
    ),
)
data class Message(
    @Id
    val id: String? = null,
    val chatId: String,
    val senderId: Long,
    val messageType: MessageType,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    var isRead: Boolean = false,
    val payload: MessagePayload,
    var clientMsgId: String? = null
) : BaseEntity()

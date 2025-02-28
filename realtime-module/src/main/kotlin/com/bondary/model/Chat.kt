package com.bondary.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "chats")
data class Chat(
    @Id
    val id: String? = null,
    var chatType: ChatType,
    val participants: List<Long>,
    val lastMessage: String? = null,
    var title: String? = null,
    var thumbnail: String? = null,
    val createdAt: Instant = Instant.now(),
    val updatedAt: Instant = Instant.now()
)

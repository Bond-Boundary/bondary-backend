package com.bondary.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "chats")
data class Chat(
    @Id
    val id: String? = null,
    val chatId: String,
    val participants: List<Long>,
    var title: String? = null,
    var thumbnail: String? = null
)

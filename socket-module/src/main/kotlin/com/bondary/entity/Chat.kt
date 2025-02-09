package com.bondary.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "chats")
data class Chat(
    @Id
    val id: String? = null,
    val title: String,
    val thumbnailId: Long,
    val userIds: List<Long>,
)

package com.bondary.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "messages")
data class UserChat(
    @Id
    val id: String? = null,
    val chatId: Long,
    val userId: Long,
    val mute: Boolean = false,
    val displayIdx: String = "",
)

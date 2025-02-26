package com.bondary.domain.message

data class ReadMessagesRequest(
    val chatId: String,
    val userId: Long,
    val messageIds: List<String>
)

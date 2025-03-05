package com.bondary.controller.v1.request

import com.bondary.model.Chat
import com.bondary.model.ChatType

data class CreateChatRequest(
    val senderId: Long,
    val receiverId: Long,
    val chatType: String,
    val thumbnail: String? = null,
) {
    fun toDocument(): Chat =
        Chat(
            chatType = ChatType.valueOf(chatType),
            participants = listOf(senderId, receiverId),
            thumbnail = thumbnail,
        )
}

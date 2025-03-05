package com.bondary.controller.v1.response

import com.bondary.model.Chat
import com.bondary.model.UserChat
import java.time.Instant

data class ChatItemResponse(
    val chatId: String,
    val chatType: String,
    val participants: List<Long>,
    val lastMessage: String?,
    val unreadCount: Int,
    val displayIdx: String,
    val updatedAt: Instant,
) {
    companion object {
        fun from(chatPairs: List<Pair<Chat, UserChat>>): List<ChatItemResponse> =
            chatPairs.map { (chat, userChat) ->
                ChatItemResponse(
                    chatId = chat.id ?: "",
                    chatType = chat.chatType.toString(),
                    participants = chat.participants,
                    lastMessage = chat.lastMessage,
                    unreadCount = userChat.unreadCount,
                    displayIdx = userChat.displayIndex,
                    updatedAt = chat.updatedAt,
                )
            }
    }
}

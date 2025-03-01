package com.bondary.service

import com.bondary.model.Chat
import com.bondary.model.UserChat
import org.springframework.stereotype.Service

@Service
class UserChatService(
    private val userChatReader: UserChatReader,
    private val chatReader: ChatReader
) {
    suspend fun findUserChats(userId: Long): List<Pair<Chat, UserChat>> {
        val userChats = userChatReader.findUserChats(userId)
        return userChats.mapNotNull { userChat ->
            chatReader.findChat(userChat.chatId)?.let { chat ->
                Pair(chat, userChat)
            }
        }
    }
}
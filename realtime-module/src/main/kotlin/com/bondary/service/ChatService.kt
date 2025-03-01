package com.bondary.service

import com.bondary.model.Chat
import kotlinx.coroutines.coroutineScope
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ChatService(
    private val chatReader: ChatReader,
    private val chatCreator: ChatCreator,
    private val chatModifier: ChatModifier,
    private val userChatCreator: UserChatCreator,
) {
    private val logger = LoggerFactory.getLogger(ChatService::class.java)

    suspend fun createChat(
        chat: Chat
    ): Chat =
        coroutineScope {
            val senderId = chat.participants[0]
            val receiverId = chat.participants[1]

            val existing = chatReader.findExistingChat(senderId, receiverId)
            if (existing != null) {
                logger.info("기존 채팅방 발견: ${existing.id}")
                return@coroutineScope existing
            }

            val created = chatCreator.createChat(senderId, receiverId, chat.chatType)
            logger.info("새로운 채팅방 생성: ${created.id}")

            userChatCreator.createUserEntries(created, senderId, receiverId)
            return@coroutineScope created
        }

    suspend fun findChat(chatId: String): Chat? {
        return chatReader.findChat(chatId)
    }
}

package com.bondary.service

import com.bondary.model.Chat
import com.bondary.model.ChatType
import kotlinx.coroutines.coroutineScope
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ChatService(
    private val chatFinder: ChatFinder,
    private val chatCreator: ChatCreator,
    private val chatModifier: ChatModifier,
    private val userChatCreator: UserChatCreator,
) {
    private val logger = LoggerFactory.getLogger(ChatService::class.java)

    suspend fun createChat(
        senderId: Long,
        receiverId: Long,
        chatType: ChatType,
    ): Chat =
        coroutineScope {
            val existing = chatFinder.findExistingChat(senderId, receiverId)
            if (existing != null) {
                logger.info("기존 채팅방 발견: ${existing.id}")
                return@coroutineScope existing
            }

            val created = chatCreator.createChat(senderId, receiverId, chatType)
            logger.info("새로운 채팅방 생성: ${created.id}")

            userChatCreator.createUserEntries(created, senderId, receiverId)
            return@coroutineScope created
        }

    suspend fun findChat(chatId: String): Chat? {
        return chatFinder.findChat(chatId)
    }
}

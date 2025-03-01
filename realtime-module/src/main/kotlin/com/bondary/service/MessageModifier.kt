package com.bondary.service

import com.bondary.repository.MessageRepository
import com.bondary.repository.UserChatRepository
import kotlinx.coroutines.reactive.awaitFirst
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class MessageModifier(
    private val messageRepository: MessageRepository,
    private val userChatRepository: UserChatRepository
) {
    private val logger = LoggerFactory.getLogger(MessageModifier::class.java)

    suspend fun markMessageAsRead(messageId: String): Boolean {
        return messageRepository.markMessageAsRead(messageId).awaitFirst()
    }

    suspend fun markAllMessagesAsRead(
        chatId: String,
        userId: Long,
    ): Boolean {
        val result = messageRepository.markAllMessageAsRead(chatId, userId).awaitFirst()
        if (result) {
            userChatRepository.resetUnreadCount(chatId, userId).awaitFirst()
        }
        logger.info("채팅방($chatId) 전체 메시지 읽음 처리 완료 for userId=$userId")
        return result
    }

}
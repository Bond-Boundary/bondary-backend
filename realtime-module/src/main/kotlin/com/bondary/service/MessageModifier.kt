package com.bondary.service

import com.bondary.repository.MessageRepository
import com.bondary.repository.UserChatRepository
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class MessageModifier(
    private val messageReader: MessageReader,
    private val webSocketService: WebSocketService,
    private val messageRepository: MessageRepository,
    private val userChatRepository: UserChatRepository
) {
    private val logger = LoggerFactory.getLogger(MessageModifier::class.java)

    suspend fun markMessageAsRead(
        messageId: String,
        userId: Long,
    ): Boolean {
        webSocketService.notifyMessageRead(messageId, userId).awaitSingleOrNull()
        return messageRepository.markMessageAsRead(messageId).awaitFirst()
    }

    suspend fun markAllMessagesAsRead(
        chatId: String,
        userId: Long,
    ): Boolean {
        val senderIds = messageReader.findUnreadMessageSenders(chatId, userId)
        webSocketService.notifyMessageAllRead(chatId, userId, senderIds).awaitSingleOrNull()

        val result = messageRepository.markAllMessageAsRead(chatId, userId).awaitFirst()
        if (result) {
            userChatRepository.resetUnreadCount(chatId, userId).awaitFirst()
        }

        logger.info("채팅방($chatId) 전체 메시지 읽음 처리 완료 for userId=$userId")
        return result
    }
}

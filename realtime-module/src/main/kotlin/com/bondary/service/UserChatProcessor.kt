package com.bondary.service

import com.bondary.repository.UserChatRepository
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.stereotype.Component

@Component
class UserChatProcessor(
    private val userChatRepository: UserChatRepository,
) {
    suspend fun incrementUnreadCount(
        receiverId: Long,
        chatId: String,
    ) {
        userChatRepository.incrementUnreadCount(receiverId, chatId).awaitFirst()
    }

    suspend fun updateDisplayIndex(
        receiverId: Long,
        chatId: String,
        messageId: String,
    ) {
        userChatRepository.updateDisplayIndex(receiverId, chatId, messageId).awaitFirst()
    }
}
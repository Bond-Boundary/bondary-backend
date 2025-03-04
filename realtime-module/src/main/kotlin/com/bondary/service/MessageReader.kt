package com.bondary.service

import com.bondary.model.Message
import com.bondary.repository.MessageRepository
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.stereotype.Component

@Component
class MessageReader(
    private val messageRepository: MessageRepository,
) {
    suspend fun findMessages(
        chatId: String,
        limit: Int,
        lastMessageId: String? = null,
    ): List<Message> {
        return messageRepository.findByChatId(chatId, limit, lastMessageId)
            .collectList()
            .awaitFirst()
    }
}
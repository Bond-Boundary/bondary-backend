package com.bondary.service

import com.bondary.repository.ChatRepository
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.stereotype.Component

@Component
class ChatModifier(
    private val chatRepository: ChatRepository,
) {
    suspend fun modifyLastMessage(
        chatId: String,
        lastMessage: String,
    ): Boolean {
        return chatRepository.modifyLastMessage(chatId, lastMessage).awaitFirst()
    }
}

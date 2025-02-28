package com.bondary.service

import com.bondary.model.Chat
import com.bondary.repository.ChatRepository
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.stereotype.Component

@Component
class ChatFinder(
    private val chatRepository: ChatRepository,
) {
    suspend fun findExistingChat(
        senderId: Long,
        receiverId: Long,
    ): Chat? {
        return chatRepository.findByParticipants(senderId)
            .filter { chat ->
                chat.participants.size == 2 && chat.participants.contains(receiverId)
            }
            .collectList()
            .awaitFirstOrNull()
            ?.firstOrNull()
    }

    suspend fun findChat(chatId: String): Chat? {
        return chatRepository.findById(chatId).awaitFirstOrNull()
    }
}

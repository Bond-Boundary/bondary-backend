package com.bondary.service

import com.bondary.model.Chat
import com.bondary.model.ChatType
import com.bondary.repository.ChatRepository
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class ChatCreator(
    private val chatRepository: ChatRepository,
) {
    suspend fun createChat(
        senderId: Long,
        receiverId: Long,
        chatType: ChatType,
    ): Chat {
        val created = Chat(
            chatType = chatType,
            participants = listOf(senderId, receiverId),
            lastMessage = null,
            createdAt = Instant.now(),
            updatedAt = Instant.now(),
        )
        return chatRepository.save(created).awaitFirst()
    }
}

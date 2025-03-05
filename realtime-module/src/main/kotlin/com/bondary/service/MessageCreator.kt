package com.bondary.service

import com.bondary.model.Message
import com.bondary.model.MessageType
import com.bondary.repository.MessageRepository
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.stereotype.Component

@Component
class MessageCreator(
    private val messageRepository: MessageRepository,
) {
    suspend fun createMessage(
        chatId: String,
        senderId: Long,
        receiverId: Long,
        content: String,
        messageType: MessageType,
    ): Message {
        val created = Message(
            chatId = chatId,
            senderId = senderId,
            receiverId = receiverId,
            content = content,
            messageType = messageType,
        )
        return messageRepository.save(created).awaitFirst()
    }
}

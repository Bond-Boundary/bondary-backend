package com.bondary.service

import com.bondary.entity.Message
import com.bondary.repository.ChatRepository
import com.bondary.repository.MessageRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.reactive.asPublisher
import org.springframework.stereotype.Service

@Service
class MessageService(
    private val sessionService: SessionService,
    private val messageRepository: MessageRepository,
    private val chatRepository: ChatRepository
) {
    suspend fun sendMessageToUser(
        senderId: Long,
        receiverId: Long,
        messageContent: String
    ) {
        val chatId =
            chatRepository.findChatIdByUserIds(listOf(senderId, receiverId))
                ?: chatRepository.createChatRoom(senderId, receiverId)

        val message =
            Message(
                chatId = chatId,
                senderId = senderId,
                receiverId = receiverId,
                text = messageContent
            )
        println("메시지 저장 - Sender: $senderId, Receiver: $receiverId, Content: $messageContent")

        messageRepository.save(message)
        sendWebSocketMessage(senderId, receiverId, messageContent)
    }

    suspend fun getMessagesBetweenUsers(
        userId: Long,
        receiverId: Long
    ): List<Message> {
        return messageRepository.findBySenderIdAndReceiverId(userId, receiverId)
    }

    suspend fun getUnreadMessagesCount(
        userId: Long,
        receiverId: Long
    ): Long {
        return messageRepository.countBySenderIdAndReceiverIdAndIsReadFalse(userId, receiverId)
    }

    suspend fun markMessagesAsRead(
        userId: Long,
        senderId: Long
    ) {
        messageRepository.findBySenderIdAndReceiverIdAndIsReadFalse(userId, senderId)
            .map { it.copy(isRead = true) }
            .forEach { messageRepository.save(it) }
    }

    private suspend fun sendWebSocketMessage(
        senderId: Long,
        receiverId: Long,
        messageContent: String
    ) {
        val receiverSession = sessionService.getWebSocketSession(receiverId)
        if (receiverSession != null) {
            println("WebSocket을 통해 메시지 전송 - Receiver: $receiverId")
            val messageFlow = flow {
                emit(receiverSession.textMessage("From $senderId: $messageContent"))
            }

            receiverSession.send(messageFlow.asPublisher())
        } else {

            println("User $receiverId is offline, 메시지 저장만 수행.")
        }
    }
}

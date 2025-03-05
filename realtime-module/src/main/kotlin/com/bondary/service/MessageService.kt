package com.bondary.service

import com.bondary.model.Message
import com.bondary.model.MessageType
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class MessageService(
    private val chatModifier: ChatModifier,
    private val messageReader: MessageReader,
    private val messageCreator: MessageCreator,
    private val messageNotifier: MessageNotifier,
    private val messageModifier: MessageModifier,
    private val userChatProcessor: UserChatProcessor,

) {
    private val logger = LoggerFactory.getLogger(MessageService::class.java)

    suspend fun sendMessage(
        chatId: String,
        senderId: Long,
        receiverId: Long,
        content: String,
        messageType: MessageType = MessageType.TEXT,
    ): Message = coroutineScope {
        runCatching {
            val created = messageCreator.createMessage(chatId, senderId, receiverId, content, messageType)
            logger.info("메시지 저장 완료: ${created.id}")

            val updateLastMessageDeferred = async { chatModifier.modifyLastMessage(chatId, content) }
            val updateReceiverChatDeferred = async {
                userChatProcessor.incrementUnreadCount(receiverId, chatId)
                userChatProcessor.updateDisplayIndex(receiverId, chatId, created.id!!)
            }
            val updateSenderChatDeferred =
                async { userChatProcessor.updateDisplayIndex(senderId, chatId, created.id!!) }

            updateLastMessageDeferred.await()
            updateReceiverChatDeferred.await()
            updateSenderChatDeferred.await()
            logger.info("채팅방 및 UserChat 업데이트 완료: chatId=$chatId")

            messageNotifier.notifyNewMessage(receiverId, created).subscribe()
            created
        }.onSuccess { message ->
            logger.info("메시지 전송 프로세스 완료: messageId=${message.id}")
        }.onFailure { error ->
            logger.error("메시지 전송 실패: chatId=$chatId, senderId=$senderId", error)
        }.getOrThrow()
    }

    suspend fun markMessageAsRead(
        messageId: String,
        userId: Long,
    ): Boolean =
        messageModifier.markMessageAsRead(messageId, userId)

    suspend fun markAllMessageAsRead(
        chatId: String,
        userId: Long,
    ): Boolean = messageModifier.markAllMessagesAsRead(chatId, userId)

    suspend fun findMessages(
        chatId: String,
        limit: Int,
        lastMessageId: String?,
    ): List<Message> = messageReader.findMessages(chatId, limit, lastMessageId)
}

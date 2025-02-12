package com.bondary.repository

import com.bondary.entity.Message
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface MessageRepository : CoroutineCrudRepository<Message, String> {
    suspend fun findByChatId(chatId: Long): List<Message>

    suspend fun findBySenderIdAndReceiverId(
        senderId: Long,
        receiverId: Long
    ): List<Message>

    suspend fun countBySenderIdAndReceiverIdAndIsReadFalse(
        senderId: Long,
        receiverId: Long
    ): Long

    suspend fun findBySenderIdAndReceiverIdAndIsReadFalse(
        senderId: Long,
        receiverId: Long
    ): List<Message>
}

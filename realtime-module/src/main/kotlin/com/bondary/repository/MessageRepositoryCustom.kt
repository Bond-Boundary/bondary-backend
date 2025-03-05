package com.bondary.repository

import com.bondary.model.Message
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface MessageRepositoryCustom {
    fun findByChatId(
        chatId: String,
        limit: Int = 50,
        lastMessageId: String?,
    ): Flux<Message>
    fun markMessageAsRead(messageId: String): Mono<Boolean>
    fun markAllMessageAsRead(
        chatId: String,
        userId: Long,
    ): Mono<Boolean>
    fun findUnreadMessageSenders(
        chatId: String,
        receiverId: Long,
    ): Mono<List<Long>>
}

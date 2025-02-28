package com.bondary.repository

import com.bondary.model.Message
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface MessageRepositoryCustom {
    fun findByChatId(chatId: String, limit: Int = 50): Flux<Message>
    fun markAsRead(messageId: String): Mono<Boolean>
}

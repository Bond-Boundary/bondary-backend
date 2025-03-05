package com.bondary.repository

import com.bondary.model.Chat
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ChatRepositoryCustom {
    fun findByParticipants(userId: Long): Flux<Chat>
    fun modifyLastMessage(chatId: String, lastMessage: String): Mono<Boolean>
}

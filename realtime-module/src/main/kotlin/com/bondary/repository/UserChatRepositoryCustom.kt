package com.bondary.repository

import com.bondary.model.UserChat
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface UserChatRepositoryCustom {
    fun findByUserIdAndChatId(
        chatId: String,
        userId: Long,
    ): Mono<UserChat>

    fun findByUserId(userId: Long): Flux<UserChat>

    fun incrementUnreadCount(
        chatId: String,
        userId: Long,
    ): Mono<Boolean>

    fun resetUnreadCount(
        chatId: String,
        userId: Long,
    ): Mono<Boolean>

    fun updateDisplayIndex(
        chatId: String,
        userId: Long,
        displayIndex: String,
    ): Mono<Boolean>
}

package com.bondary.repository

import com.bondary.model.UserChat
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface UserChatRepositoryCustom {
    fun findByUserIdAndChatId(userId: Long, chatId: String): Mono<UserChat>
    fun findByUserId(userId: Long): Flux<UserChat>
    fun incrementUnreadCount(userId: Long, chatId: String): Mono<Boolean>
    fun resetUnreadCount(userId: Long, chatId: String): Mono<Boolean>
    fun updateDisplayIndex(userId: Long, chatId: String, displayIndex: String): Mono<Boolean>
}

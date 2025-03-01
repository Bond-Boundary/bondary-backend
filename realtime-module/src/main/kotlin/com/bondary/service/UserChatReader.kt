package com.bondary.service

import com.bondary.model.UserChat
import com.bondary.repository.UserChatRepository
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.stereotype.Component

@Component
class UserChatReader(
    private val userChatRepository: UserChatRepository,
) {

    suspend fun findUserChat(
        chatId: String,
        userId: Long,
    ): UserChat? {
        return userChatRepository.findByUserIdAndChatId(chatId, userId)
            .awaitFirstOrNull()
    }

    suspend fun findUserChats(userId: Long): List<UserChat> {
        return userChatRepository.findByUserId(userId)
            .collectList()
            .awaitFirst()
    }
}
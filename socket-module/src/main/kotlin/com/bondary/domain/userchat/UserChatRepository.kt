package com.bondary.domain.userchat

import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserChatRepository : CoroutineCrudRepository<UserChat, String> {
    suspend fun findByUserIdAndDisplayIdxLessThanOrderByDisplayIdxDesc(
        userId: Long,
        displayIdx: String,
        size: Int
    ): Flow<UserChat>

    suspend fun findByUserIdOrderByDisplayIdxDesc(
        userId: Long,
        size: Int
    ): Flow<UserChat>

    suspend fun findByChatId(chatId: String): Flow<UserChat>

    suspend fun findByUserId(userId: Long): Flow<UserChat>

    suspend fun findByChatIdAndUserId(
        chatId: String,
        userId: Long
    ): UserChat

    suspend fun deleteByChatIdAndUserId(
        chatId: String,
        userId: Long
    )
}

package com.bondary.repository

import com.bondary.entity.Chat
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface ChatRepository : CoroutineCrudRepository<Chat, String> {
    suspend fun findByUserIds(userIds: List<Long>): Chat?

    fun findChatIdByUserIds(userIds: List<Long>): Long

    fun createChatRoom(
        senderId: Long,
        receiverId: Long
    ): Long
}

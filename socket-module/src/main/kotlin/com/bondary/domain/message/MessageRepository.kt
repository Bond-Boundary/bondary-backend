package com.bondary.domain.message

import kotlinx.coroutines.flow.Flow
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.Update
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MessageRepository : CoroutineCrudRepository<Message, String> {
    // 특정 메시지들 읽음 처리
    @Query("{ 'id': { \$in: ?0 } }")
    @Update("{ '\$set': { 'isRead': true } }")
    suspend fun markMessagesAsRead(messageIds: List<String>): Long

    @Query("{ 'chatId': ?0, 'senderId': { \$ne: ?1 }, 'isRead': false }")
    suspend fun findUnreadMessages(chatId: String, userId: Long): List<Message>

    // 채팅방의 안 읽은 메시지 수 조회
    @Query("{ 'chatId': ?0, 'senderId': { \$ne: ?1 }, 'isRead': false }", count = true)
    suspend fun countUnreadMessages(
        chatId: String,
        userId: Long
    ): Long?

    @Query("{ 'chatId': ?0, 'id': { \$lt: ?1 } }")
    suspend fun findByChatIdAndIdLessThanOrderByTimestampDesc(
        chatId: String,
        id: String,
        size: Int
    ): Flow<Message>

    @Query("{ 'chatId': ?0 }")
    suspend fun findByChatIdOrderByTimestampDesc(
        chatId: String,
        size: Int
    ): Flow<Message>

}

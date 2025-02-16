package com.bondary.domain.userchat

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserChatRepository : MongoRepository<UserChat, String> {
    suspend fun findByUserIdAndDisplayIdxLessThanOrderByDisplayIdxDesc(
        userId: Long,
        displayIdx: String,
        size: Int
    ): List<UserChat>

    suspend fun findByUserIdOrderByDisplayIdxDesc(
        userId: Long,
        size: Int
    ): List<UserChat>
}

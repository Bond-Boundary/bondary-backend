package com.bondary.repository

import com.bondary.entity.UserChat
import org.springframework.data.mongodb.repository.MongoRepository

interface UserChatRepository : MongoRepository<UserChat, String> {
    fun findByUserId(userId: Long): UserChat
}

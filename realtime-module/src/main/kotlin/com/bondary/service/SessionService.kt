package com.bondary.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class SessionService(
    private val redisTemplate: RedisTemplate<String, String>
) {
    /**
     * key는 "user:session:{userId}"이고 value는 세션 정보(연결된 서버 정보 등)로 저장
     */
    suspend fun setUserSession(userId: Long, sessionInfo: String) {
        withContext(Dispatchers.IO) {
            redisTemplate.opsForValue().set("user:session:$userId", sessionInfo)
        }
    }

    suspend fun getUserSession(userId: Long) {
        withContext(Dispatchers.IO) {
            redisTemplate.opsForValue().get("user:session:$userId")
        }
    }

    suspend fun removeUserSession(userId: Long) {
        withContext(Dispatchers.IO) {
            redisTemplate.delete("user:session:$userId")
        }
    }


}

package com.bondary.service

import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Service
import org.springframework.web.reactive.socket.WebSocketSession
import java.util.concurrent.ConcurrentHashMap

@Service
class SessionService(
    private val redisTemplate: ReactiveRedisTemplate<String, String>
) {
    // 로컬 메모리: 사용자 ID -> WebSocket 세션 매핑
    private val userSessions = ConcurrentHashMap<Long, WebSocketSession>()

    fun saveUserServerMapping(
        userId: Long,
        serverIp: String
    ) {
        redisTemplate.opsForValue().set("user:$userId", serverIp).subscribe()
    }

    fun deleteUserServerMapping(userId: Long) {
        redisTemplate.delete("user:$userId").subscribe()
    }

    suspend fun getUserServerMapping(userId: Long): String? {
        return redisTemplate.opsForValue().get("user:$userId").awaitSingleOrNull()
    }

    fun registerWebSocketSession(
        userId: Long,
        session: WebSocketSession
    ) {
        userSessions[userId] = session
    }

    fun removeWebSocketSession(userId: Long) {
        userSessions.remove(userId)
    }

    fun getWebSocketSession(userId: Long): WebSocketSession? {
        return userSessions[userId]
    }
}

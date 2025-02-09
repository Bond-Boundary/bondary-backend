package com.bondary.service

import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Service
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono
import java.util.concurrent.ConcurrentHashMap

@Service
class SessionService(
    private val redisTemplate: ReactiveRedisTemplate<String, String>,
) {
    // 로컬 메모리: 사용자 ID -> WebSocket 세션 매핑
    private val userSessions = ConcurrentHashMap<Long, WebSocketSession>()

    // Redis: 사용자 ID -> 서버 IP 매핑 저장
    fun saveUserServerMapping(
        userId: Long,
        serverIp: String,
    ): Mono<Boolean> {
        return redisTemplate.opsForValue().set("user:$userId", serverIp)
    }

    // Redis: 사용자 ID -> 서버 IP 매핑 삭제
    fun deleteUserServerMapping(userId: Long): Mono<Long> {
        return redisTemplate.delete("user:$userId")
    }

    // Redis: 사용자 ID -> 서버 IP 매핑 조회
    fun getUserServerMapping(userId: Long): Mono<String?> {
        return redisTemplate.opsForValue().get("user:$userId")
    }

    // WebSocket 세션 등록
    fun registerWebSocketSession(
        userId: Long,
        session: WebSocketSession,
    ) {
        userSessions[userId] = session
    }

    // WebSocket 세션 삭제
    fun removeWebSocketSession(userId: Long) {
        userSessions.remove(userId)
    }

    // WebSocket 세션 조회
    fun getWebSocketSession(userId: Long): WebSocketSession? {
        return userSessions[userId]
    }
}

package com.bondary.websocket

import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class ServerLocationManager(
    private val reactiveStringRedisTemplate: ReactiveRedisTemplate<String, String>,
    @Value("\${server.host}") private val serverIP: String
) {
    suspend fun saveUserServerLocation(userId: Long) {
        reactiveStringRedisTemplate.opsForValue()
            .set("user:server:$userId", serverIP)
            .awaitSingle()
    }

    suspend fun getUserServerLocation(userId: Long): String? {
        return reactiveStringRedisTemplate.opsForValue()
            .get("user:server:$userId")
            .awaitSingleOrNull()
    }

    suspend fun removeUserServerLocation(userId: Long) {
        reactiveStringRedisTemplate.opsForValue()
            .delete("user:server:$userId")
            .awaitSingle()
    }

    suspend fun updateLastPongTime(userId: Long) {
        reactiveStringRedisTemplate.opsForValue()
            .set("user:pong:$userId", LocalDateTime.now().toString())
            .awaitSingle()
    }

    suspend fun getLastPongTime(userId: Long): LocalDateTime? {
        return reactiveStringRedisTemplate.opsForValue()
            .get("user:pong:$userId")
            .awaitSingleOrNull()
            ?.let { LocalDateTime.now() }
    }
}

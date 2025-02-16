package com.bondary.websocket

import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Component

@Component
class ServerLocationManager(
    private val reactiveStringRedisTemplate: ReactiveRedisTemplate<Long, String>,
    @Value("\${server.host}") private val serverIP: String
) {
    suspend fun saveUserServerLocation(userId: Long) {
        reactiveStringRedisTemplate.opsForValue()
            .set(userId, serverIP)
            .awaitSingle()
    }

    suspend fun getUserServerLocation(userId: Long): String? {
        return reactiveStringRedisTemplate.opsForValue()
            .get(userId)
            .awaitSingleOrNull()
    }

    suspend fun removeUserServerLocation(userId: Long) {
        reactiveStringRedisTemplate.opsForValue()
            .delete(userId)
            .awaitSingle()
    }
}

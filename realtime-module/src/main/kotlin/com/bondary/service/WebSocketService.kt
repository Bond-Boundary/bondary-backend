package com.bondary.service

import com.bondary.model.Message
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class WebSocketService(
    private val sessionRegistry: SessionRegistry,
    private val objectMapper: ObjectMapper
) {
    private val logger = LoggerFactory.getLogger(WebSocketService::class.java)

    object EventType {
        const val NEW_MESSAGE = "NEW_MESSAGE"
        const val MESSAGE_READ = "MESSAGE_READ"
        const val MESSAGE_ALL_READ = "MESSAGE_ALL_READ"
    }

    fun notifyMessage(
        userId: Long,
        message: Message,
    ): Mono<Void> {
        val event = mapOf(
            "type" to EventType.NEW_MESSAGE,
            "data" to message
        )
        val session = sessionRegistry.getUserSession(userId)
        if (session != null && session.isOpen) {
            val payload = objectMapper.writeValueAsString(event)
            return session.send(Mono.just(session.textMessage(payload)))
                .doOnSuccess { logger.info("WebSocket 메시지 전송 성공: userId=$userId, messageId=${message.id}") }
                .doOnError { logger.error("WebSocket 메시지 전송 실패: userId=$userId", it) }
                .then()
        }
        return Mono.empty()
    }

    fun isUserOnline(userId: Long): Boolean = sessionRegistry.isUserOnline(userId)
}
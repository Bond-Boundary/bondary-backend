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

    // 범용 이벤트 전송 함수
    suspend fun notifyEvent(userId: Long, event: Map<String, Any>): Mono<Void> {
        val session = sessionRegistry.getUserSession(userId)
        return if (session != null && session.isOpen) {
            val payload = objectMapper.writeValueAsString(event)
            session.send(Mono.just(session.textMessage(payload)))
                .doOnSuccess { logger.info("이벤트 전송 성공: userId=$userId, event=$event") }
                .doOnError { logger.error("이벤트 전송 실패: userId=$userId, event=$event", it) }
                .then()
        } else {
            Mono.empty()
        }
    }

    suspend fun notifyMessage(
        message: Message,
        userId: Long,
    ): Mono<Void> {
        val event = mapOf("type" to EventType.NEW_MESSAGE, "data" to message)
        return notifyEvent(userId, event)
    }

    suspend fun notifyMessageRead(
        messageId: String,
        userId: Long
    ): Mono<Void> {
        val event = mapOf("type" to EventType.MESSAGE_READ, "messageId" to messageId)
        return notifyEvent(userId, event)
    }

    suspend fun notifyMessageAllRead(
        chatId: String,
        userId: Long,
        senderIds: List<Long>
    ): Mono<Void> {
        try {
            // 발신자가 없으면 빈 Mono 반환
            if (senderIds.isEmpty()) {
                logger.info("채팅방($chatId)에서 읽지 않은 메시지의 발신자가 없습니다.")
                return Mono.empty()
            }

            logger.info("채팅방($chatId)의 메시지 발신자들에게 읽음 알림 전송: $senderIds")

            // 각 발신자에게 알림 전송
            val notifications = senderIds.map { senderId ->
                val event = mapOf("type" to EventType.MESSAGE_ALL_READ, "chatId" to chatId)
                notifyEvent(senderId, event)
            }

            // 모든 알림을 하나의 Mono로 결합
            return Mono.`when`(notifications)
                .doOnSuccess { logger.info("채팅방($chatId) 읽음 알림 전송 완료") }
                .doOnError { logger.error("채팅방($chatId) 읽음 알림 전송 실패", it) }
        } catch (e: Exception) {
            logger.error("메시지 읽음 알림 처리 중 오류", e)
            return Mono.error(e)
        }
    }

    fun isUserOnline(userId: Long): Boolean = sessionRegistry.isUserOnline(userId)
}

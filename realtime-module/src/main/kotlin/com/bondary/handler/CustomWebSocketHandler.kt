package com.bondary.handler

import com.bondary.model.MessageType
import com.bondary.service.MessageService
import com.bondary.service.SessionRegistry
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.reactor.mono
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration
import java.util.concurrent.ConcurrentHashMap

@Component
class CustomWebSocketHandler(
    private val messageService: MessageService,
    private val sessionRegistry: SessionRegistry,
    private val objectMapper: ObjectMapper,
) : WebSocketHandler {
    private val logger = LoggerFactory.getLogger(CustomWebSocketHandler::class.java)
    private val sessions = ConcurrentHashMap<Long, WebSocketSession>()

    override fun handle(session: WebSocketSession): Mono<Void> {
        val userId = getUserIdFromSession(session) ?: return session.close()
        logger.info("WebSocket 연결 수립: userId=$userId")

        // 동일 사용자의 기존 세션이 있다면, 종료 후 새 세션 저장
        val closeExistingSession = sessionRegistry.getUserSession(userId)?.takeIf { it.isOpen }
            ?.close()
            ?.doOnSuccess { logger.info("기존 세션 종료: userId=$userId") }
            ?.doOnError { e -> logger.warn("기존 세션 종료 중 오류: ${e.message}") }
            ?: Mono.empty()

        return closeExistingSession
            .doOnSuccess { sessionRegistry.setUserSession(userId, session) }
            .then(setupWebSocketCommunication(session, userId))
    }

    private fun setupWebSocketCommunication(
        session: WebSocketSession,
        userId: Long,
    ): Mono<Void> {
        // ping
        val pingFlux = Flux.interval(Duration.ofSeconds(30))
            .map { session.pingMessage { it.wrap(byteArrayOf()) } }
            .doOnError { e -> logger.error("Ping 전송 오류", e) }

        // Inbound: 클라이언트로부터 들어오는 메시지 처리
        val inbound = session.receive()
            .map { it.payloadAsText }
            .flatMap { payload -> handleIncomingMessage(userId, payload) }
            .doFinally {
                if (sessions[userId]?.id == session.id) {
                    sessions.remove(userId)
                    logger.info("WebSocket 연결 종료: userId=$userId")
                }
            }
            .then()

        // Outbound: pingFlux를 session.send()로 전송
        val outbound = session.send(pingFlux)
        return Mono.`when`(inbound, outbound)
    }

    private fun getUserIdFromSession(session: WebSocketSession): Long? {
        return session.handshakeInfo.uri.query
            ?.split("&")
            ?.find { it.startsWith("userId=") }
            ?.substringAfter("userId=")
            ?.toLongOrNull()
    }

    private fun handleIncomingMessage(
        userId: Long,
        payload: String,
    ): Mono<Void> {
        return mono {
            try {
                val message = objectMapper.readTree(payload)
                val messageType = message.get("type")?.asText() ?: return@mono

                when (messageType) {
                    "NEW_MESSAGE" -> {
                        val chatId = message.get("chatId")?.asText() ?: return@mono
                        val receiverId = message.get("receiverId")?.asLongOrNull() ?: return@mono
                        val content = message.get("content")?.asText() ?: return@mono
                        val msgType = MessageType.valueOf(
                            message.get("messageType")?.asText() ?: "TEXT",
                        )
                        messageService.sendMessage(chatId, userId, receiverId, content, msgType)
                    }
                    "MESSAGE_READ" -> {
                        val messageId = message.get("messageId")?.asText() ?: return@mono
                        messageService.markMessageAsRead(messageId, userId)
                    }
                    "MESSAGE_ALL_READ" -> {
                        val chatId = message.get("chatId")?.asText() ?: return@mono
                        messageService.markAllMessageAsRead(chatId, userId)
                    }
                    else -> Unit
                }
            } catch (e: Exception) {
                println("메시지 프로세싱 중 에러 발생: ${e.message}")
            }
        }.then()
    }

    // 특정 사용자에게 메시지 전송
    fun sendMessageToUser(
        userId: Long,
        message: Any,
    ): Mono<Void> {
        val session = sessionRegistry.getUserSession(userId) ?: return Mono.empty()
        if (!session.isOpen) {
            sessionRegistry.removeUserSession(userId)
            return Mono.empty()
        }
        val payload = objectMapper.writeValueAsString(message)
        return session.send(Mono.just(session.textMessage(payload)))
    }

    private fun JsonNode?.asLongOrNull(): Long? {
        return this?.asLong()
    }
}

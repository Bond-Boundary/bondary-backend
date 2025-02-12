package com.bondary.handler

import com.bondary.service.MessageService
import com.bondary.service.SessionService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlinx.coroutines.reactive.collect
import kotlinx.coroutines.reactor.mono
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession

@Component
class ChatWebSocketHandler(
    private val sessionService: SessionService,
    private val messageService: MessageService
) : WebSocketHandler {
    override fun handle(session: WebSocketSession) =
        mono {
            val queryParams =
                session.handshakeInfo.uri.query.split("&")
                    .associate { it.split("=").let { (key, value) -> key to value } }

            val userId =
                queryParams["userId"]?.toLongOrNull()
                    ?: throw IllegalArgumentException("Invalid userId")

            println("WebSocket 연결됨 - userId: $userId, sessionId: ${session.id}")

            val serverIp = "127.0.0.1" // (실제 환경에서는 동적으로 설정)

            sessionService.registerWebSocketSession(userId, session)
            sessionService.saveUserServerMapping(userId, serverIp)

            try {
                session.receive()
                    .map { it.payloadAsText }
                    .collect { message ->
                        processMessage(userId, message)
                    }
            } finally {
                sessionService.removeWebSocketSession(userId)
                sessionService.deleteUserServerMapping(userId)
                println("WebSocket 연결 종료 - userId: $userId")
            }
        }.then()

    private suspend fun processMessage(
        userId: Long,
        payload: String
    ) {
        println("WebSocket 메시지 수신 - User: $userId, Payload: $payload")
        val messageJson = jacksonObjectMapper().readTree(payload)

        val messageType = messageJson["type"]?.asText()
        if (messageType == "READ_CONFIRMATION") {
            val receiverId =
                messageJson["receiverId"]?.asLong()
                    ?: throw IllegalArgumentException("Receiver ID가 없습니다.")

            println("읽음 확인 처리 - Sender: $userId, Receiver: $receiverId")
            messageService.markMessagesAsRead(userId, receiverId)
            return
        }

        val receiverId =
            messageJson["receiverId"]?.asLong()
                ?: throw IllegalArgumentException("Receiver ID가 없습니다.")

        val content =
            messageJson["content"]?.asText()
                ?: throw IllegalArgumentException("메시지 내용이 없습니다.")

        println("메시지 전송 시도 - Sender: $userId, Receiver: $receiverId, Content: $content")

        messageService.sendMessageToUser(userId, receiverId, content)
    }
}

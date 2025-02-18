package com.bondary.websocket

import com.bondary.domain.message.Message
import com.bondary.domain.message.MessagePayload
import com.bondary.domain.message.MessageService
import com.bondary.domain.message.MessageType
import com.bondary.websocket.dto.WebSocketMessage
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.*
import org.springframework.stereotype.Component
import org.springframework.web.socket.BinaryMessage
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.AbstractWebSocketHandler

@Component
class ChatWebSocketHandler(
    private val objectMapper: ObjectMapper,
    private val sessionManager: WebSocketSessionManager,
    private val serverLocationManager: ServerLocationManager,
    private val messageService: MessageService,
    private val fileHandler: ChatFileHandler
) : AbstractWebSocketHandler() {
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun afterConnectionEstablished(session: WebSocketSession) {
        session.getUserId()?.let { userId ->
            scope.launch {
                sessionManager.addSession(userId, session)
                serverLocationManager.saveUserServerLocation(userId)
            }
        }
    }

    override fun afterConnectionClosed(
        session: WebSocketSession,
        status: CloseStatus
    ) {
        session.getUserId()?.let { userId ->
            scope.launch {
                sessionManager.removeSession(userId)
                serverLocationManager.removeUserServerLocation(userId)
            }
        }
    }

    override fun handleTextMessage(
        session: WebSocketSession,
        message: TextMessage
    ) {
        val webSocketMessage = objectMapper.readValue(message.payload, WebSocketMessage::class.java)
        scope.launch {
            handleWebSocketMessage(session, webSocketMessage)
        }
    }

    override fun handleBinaryMessage(
        session: WebSocketSession,
        message: BinaryMessage
    ) {
        fileHandler.handleFileUpload(session, message)
    }

    private fun startPingScheduler(session: WebSocketSession) {
        scope.launch {
            while (session.isOpen) {
                try {
                    session.getUserId()?.let { userId ->
                        val pingMessage =
                            WebSocketMessage.PingMessage(
                                senderId = userId,
                                timestamp = System.currentTimeMillis(),
                            )
                        session.sendMessage(TextMessage(objectMapper.writeValueAsString(pingMessage)))
                    }
                    delay(3000)
                } catch (e: Exception) {
                    session.close()
                    break
                }
            }
        }
    }

    private suspend fun handleWebSocketMessage(
        session: WebSocketSession,
        webSocketMessage: WebSocketMessage
    ) {
        when (webSocketMessage) {
            is WebSocketMessage.ReadMessage -> {}
            is WebSocketMessage.PingMessage -> {}
            is WebSocketMessage.PongMessage -> {
//                val roundTripTime = System.currentTimeMillis() - webSocketMessage.pingTimestamp
//                if (roundTripTime > 5000) { // 5초 이상 걸리면 로그
//                    logger.warn("High latency detected: ${roundTripTime}ms")
//                }
                session.getUserId()?.let { userId -> serverLocationManager.updateLastPongTime(userId) }
            }
            is WebSocketMessage.TextMessage -> {
                val message =
                    Message(
                        chatId = webSocketMessage.chatId,
                        senderId = webSocketMessage.senderId,
                        messageType = MessageType.TEXT,
                        payload =
                            MessagePayload.TextPayload(
                                content = webSocketMessage.content,
                            ),
                    )
                messageService.saveMessage(message)
            }

            is WebSocketMessage.FileMessage -> {
                fileHandler.prepareFileUpload(session, webSocketMessage)
            }

            is WebSocketMessage.ImageMessage -> {
                fileHandler.prepareFileUpload(session, webSocketMessage)
            }

            is WebSocketMessage.SystemMessage -> {
                val message =
                    Message(
                        chatId = webSocketMessage.chatId,
                        senderId = webSocketMessage.senderId,
                        messageType = MessageType.SYSTEM,
                        payload =
                            MessagePayload.SystemPayload(
                                systemMessageType = webSocketMessage.systemMessageType,
                                content = webSocketMessage.content,
                            ),
                    )
                messageService.saveMessage(message)
            }
        }
    }

    private fun WebSocketSession.getUserId(): Long? = attributes["userId"] as? Long
}

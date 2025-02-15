package com.bondary.websocket

import com.bondary.domain.message.Message
import com.bondary.domain.message.MessagePayload
import com.bondary.domain.message.MessageService
import com.bondary.domain.message.MessageType
import com.bondary.websocket.dto.WebSocketMessage
import com.fasterxml.jackson.databind.ObjectMapper
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
    private val messageService: MessageService,
    private val fileHandler: ChatFileHandler
) : AbstractWebSocketHandler() {
    override fun afterConnectionEstablished(session: WebSocketSession) {
        session.getUserId()?.let { userId ->
            sessionManager.addSession(userId, session)
        }
    }

    override fun handleTextMessage(
        session: WebSocketSession,
        message: TextMessage
    ) {
        val webSocketMessage = objectMapper.readValue(message.payload, WebSocketMessage::class.java)
        handleWebSocketMessage(session, webSocketMessage)
    }

    override fun handleBinaryMessage(
        session: WebSocketSession,
        message: BinaryMessage
    ) {
        fileHandler.handleFileUpload(session, message)
    }

    override fun afterConnectionClosed(
        session: WebSocketSession,
        status: CloseStatus
    ) {
        session.getUserId()?.let { userId ->
            sessionManager.removeSession(userId)
        }
    }

    private fun handleWebSocketMessage(
        session: WebSocketSession,
        webSocketMessage: WebSocketMessage
    ) {
        when (webSocketMessage) {
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

    private fun WebSocketSession.getUserId(): String? = attributes["userId"] as? String
}

package com.bondary.websocket

import com.bondary.domain.message.Message
import com.bondary.domain.message.MessagePayload
import com.bondary.domain.message.MessageService
import com.bondary.domain.message.MessageType
import com.bondary.domain.userchat.UserChatRepository
import com.bondary.websocket.dto.UnreadInfo
import com.bondary.websocket.dto.WebSocketMessage
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.annotation.PreDestroy
import kotlinx.coroutines.*
import org.slf4j.LoggerFactory
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
    private val fileHandler: ChatFileHandler,
    private val userChatRepository: UserChatRepository
) : AbstractWebSocketHandler() {
    private val logger = LoggerFactory.getLogger(ChatWebSocketHandler::class.java)
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun afterConnectionEstablished(session: WebSocketSession) {
        val userId = extractUserID(session)
        userId.let { id ->
            session.attributes["userId"] = id

            scope.launch {
                sessionManager.addSession(userId, session)
                startPingScheduler(session)

                // 사용자의 모든 채팅방에서 안 읽은 메시지 수 조회
                val userChats = withContext(Dispatchers.IO) {
                    userChatRepository.findByUserId(userId)
                }
                userChats.collect { userChat ->
                    val unreadCount = messageService.countUnreadMessages(userChat.chatId, userId)
                    if (unreadCount > 0) {
                        val unreadInfo = UnreadInfo(
                            chatId = userChat.chatId,
                            count = unreadCount,
                        )
                        sendMessageSafely(session, unreadInfo)
                    }
                }
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
        scope.launch { fileHandler.handleFileUpload(session, message) }
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
                    delay(30000)
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
            is WebSocketMessage.ReadMessage -> {
                session.getUserId()?.let { userId ->
                    messageService.markMessagesAsRead(
                        chatId = webSocketMessage.chatId,
                        userId = userId
                    )
                }
            }
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
                messageService.saveAndSendMessage(message)
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
                messageService.saveAndSendMessage(message)
            }
        }
    }

    private fun sendMessageSafely(
        session: WebSocketSession,
        message: Any
    ) {
        if (session.isOpen) {
            session.sendMessage(TextMessage(objectMapper.writeValueAsString(message)))
        } else {
            logger.warn("WebSocket 세션이 닫혀 있어 메시지를 보낼 수 없습니다.")
        }
    }

    private fun extractUserID(session: WebSocketSession): Long {
        val userIdStr = session.uri?.query
            ?.split("&")
            ?.find { it.startsWith("userId=") }
            ?.substringAfter("userId=")

        return userIdStr?.toLongOrNull() ?: -1L
    }

    private fun WebSocketSession.getUserId(): Long? = attributes["userId"] as? Long

    @PreDestroy
    fun shutdown() {
        scope.cancel()
    }

}

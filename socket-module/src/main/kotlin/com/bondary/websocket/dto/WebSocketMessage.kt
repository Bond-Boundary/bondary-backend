package com.bondary.websocket.dto

import com.bondary.domain.message.MessageType
import com.bondary.domain.message.SystemMessageType

sealed class WebSocketMessage {
    abstract val type: MessageType
    abstract val chatId: Long
    abstract val senderId: Long

    data class TextMessage(
        override val type: MessageType = MessageType.TEXT,
        override val chatId: Long,
        override val senderId: Long,
        val content: String
    ) : WebSocketMessage()

    data class FileMessage(
        override val type: MessageType = MessageType.FILE,
        override val chatId: Long,
        override val senderId: Long,
        val fileName: String,
        val fileSize: Long
    ) : WebSocketMessage()

    data class ImageMessage(
        override val type: MessageType = MessageType.IMAGE,
        override val chatId: Long,
        override val senderId: Long,
        val fileName: String,
        val fileSize: Long
    ) : WebSocketMessage()

    data class SystemMessage(
        override val type: MessageType = MessageType.SYSTEM,
        override val chatId: Long,
        override val senderId: Long,
        val systemMessageType: SystemMessageType,
        val content: String
    ) : WebSocketMessage()

    data class PingMessage(
        override val type: MessageType = MessageType.PING,
        override val chatId: Long = -1,
        override val senderId: Long,
        val timestamp: Long = System.currentTimeMillis()
    ) : WebSocketMessage()

    data class PongMessage(
        override val type: MessageType = MessageType.PONG,
        override val chatId: Long = -1,
        override val senderId: Long,
        val pingTimestamp: Long
    ) : WebSocketMessage()

    data class ReadMessage(
        override val type: MessageType = MessageType.READ,
        override val chatId: Long,
        override val senderId: Long,
        val messageIds: List<Long>
    ): WebSocketMessage()
}

package com.bondary.websocket.dto

import com.bondary.domain.message.SystemMessageType

sealed class WebSocketMessage {
    abstract val chatId: Long
    abstract val senderId: Long

    data class TextMessage(
        override val chatId: Long,
        override val senderId: Long,
        val content: String
    ) : WebSocketMessage()

    data class FileMessage(
        override val chatId: Long,
        override val senderId: Long,
        val fileName: String,
        val fileSize: Long
    ) : WebSocketMessage()

    data class ImageMessage(
        override val chatId: Long,
        override val senderId: Long,
        val fileName: String,
        val fileSize: Long
    ) : WebSocketMessage()

    data class SystemMessage(
        override val chatId: Long,
        override val senderId: Long,
        val systemMessageType: SystemMessageType,
        val content: String
    ) : WebSocketMessage()

    data class PingMessage(
        override val chatId: Long,
        override val senderId: Long,
        val timestamp: Long = System.currentTimeMillis()
    ) : WebSocketMessage()

    data class PongMessage(
        override val chatId: Long,
        override val senderId: Long,
        val pingTimestamp: Long
    ) : WebSocketMessage()
}

package com.bondary.websocket.dto

import com.bondary.domain.message.SystemMessageType

sealed class WebSocketMessage {
    abstract val chatId: String
    abstract val senderId: String

    data class TextMessage(
        override val chatId: String,
        override val senderId: String,
        val content: String
    ) : WebSocketMessage()

    data class FileMessage(
        override val chatId: String,
        override val senderId: String,
        val fileName: String,
        val fileSize: Long
    ) : WebSocketMessage()

    data class ImageMessage(
        override val chatId: String,
        override val senderId: String,
        val fileName: String,
        val fileSize: Long
    ) : WebSocketMessage()

    data class SystemMessage(
        override val chatId: String,
        override val senderId: String,
        val systemMessageType: SystemMessageType,
        val content: String
    ) : WebSocketMessage()
}

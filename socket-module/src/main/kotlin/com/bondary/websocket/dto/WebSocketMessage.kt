package com.bondary.websocket.dto

import com.bondary.domain.message.MessageType
import com.bondary.domain.message.SystemMessageType
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes(
    JsonSubTypes.Type(value = WebSocketMessage.TextMessage::class, name = "TEXT"),
    JsonSubTypes.Type(value = WebSocketMessage.PingMessage::class, name = "PING"),
    JsonSubTypes.Type(value = WebSocketMessage.PongMessage::class, name = "PONG"),
    JsonSubTypes.Type(value = WebSocketMessage.ReadMessage::class, name = "READ"),
    JsonSubTypes.Type(value = WebSocketMessage.FileMessage::class, name = "FILE"),
    JsonSubTypes.Type(value = WebSocketMessage.ImageMessage::class, name = "IMAGE"),
    JsonSubTypes.Type(value = WebSocketMessage.SystemMessage::class, name = "SYSTEM")
)
sealed class WebSocketMessage {
    abstract val type: MessageType
    abstract val chatId: String
    abstract val senderId: Long

    data class TextMessage(
        override val type: MessageType = MessageType.TEXT,
        override val chatId: String,
        override val senderId: Long,
        val content: String,
    ) : WebSocketMessage()

    data class FileMessage(
        override val type: MessageType = MessageType.FILE,
        override val chatId: String,
        override val senderId: Long,
        val fileName: String,
        val fileSize: Long,
    ) : WebSocketMessage()

    data class ImageMessage(
        override val type: MessageType = MessageType.IMAGE,
        override val chatId: String,
        override val senderId: Long,
        val fileName: String,
        val fileSize: Long,
    ) : WebSocketMessage()

    data class SystemMessage(
        override val type: MessageType = MessageType.SYSTEM,
        override val chatId: String,
        override val senderId: Long,
        val systemMessageType: SystemMessageType,
        val content: String,
    ) : WebSocketMessage()

    data class PingMessage(
        override val type: MessageType = MessageType.PING,
        override val chatId: String = "-1",
        override val senderId: Long,
        val timestamp: Long = System.currentTimeMillis(),
    ) : WebSocketMessage()

    data class PongMessage(
        override val type: MessageType = MessageType.PONG,
        override val chatId: String = "-1",
        override val senderId: Long,
        val pingTimestamp: Long,
    ) : WebSocketMessage()

    data class ReadMessage(
        override val type: MessageType = MessageType.READ,
        override val chatId: String,
        override val senderId: Long,
        val messageIds: List<String> = emptyList(),
    ) : WebSocketMessage()
}

package com.bondary.domain.message

import com.bondary.websocket.dto.WebSocketMessage
import org.springframework.stereotype.Component

@Component
class MessageCreator {
    fun createMessageEvent(message: Message): WebSocketMessage =
        when (val payload = message.payload) {
            is MessagePayload.TextPayload -> createTextMessageEvent(message, payload)
            is MessagePayload.ReadPayload -> createReadMessageEvent(message)
            is MessagePayload.ImagePayload -> createImageMessageEvent(message, payload)
            is MessagePayload.FilePayload -> createFileMessageEvent(message, payload)
            is MessagePayload.SystemPayload -> createSystemMessageEvent(message, payload)
        }

    private fun createTextMessageEvent(
        message: Message,
        payload: MessagePayload.TextPayload
    ) = WebSocketMessage.TextMessage(
        chatId = message.chatId,
        senderId = message.senderId,
        content = payload.content,
    )

    private fun createReadMessageEvent(
        message: Message,
    ) = WebSocketMessage.ReadMessage(
        chatId = message.chatId,
        senderId = message.senderId,
        messageIds = (message.payload as? MessagePayload.ReadPayload)?.messageIds ?: emptyList(),
    )

    private fun createSystemMessageEvent(
        message: Message,
        payload: MessagePayload.SystemPayload
    ) = WebSocketMessage.SystemMessage(
        chatId = message.chatId,
        senderId = message.senderId,
        systemMessageType = payload.systemMessageType,
        content = payload.content,
    )

    private fun createFileMessageEvent(
        message: Message,
        payload: MessagePayload.FilePayload
    ) = WebSocketMessage.FileMessage(
        chatId = message.chatId,
        senderId = message.senderId,
        fileName = payload.fileName,
        fileSize = payload.fileSize.toLongOrNull() ?: 0,
    )

    private fun createImageMessageEvent(
        message: Message,
        payload: MessagePayload.ImagePayload
    ) = WebSocketMessage.ImageMessage(
        chatId = message.chatId,
        senderId = message.senderId,
        fileName = payload.imageUrl,
        fileSize = 0, // 크기 정보가 없음
    )

}

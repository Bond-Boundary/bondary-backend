package com.bondary.service

import com.bondary.handler.CustomWebSocketHandler
import com.bondary.model.Message
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class WebSocketService(
    private val customWebSocketHandler: CustomWebSocketHandler
) {
    object EventType {
        const val NEW_MESSAGE = "NEW_MESSAGE"
        const val MESSAGE_READ = "MESSAGE_READ"
        const val MESSAGE_ALL_READ = "MESSAGE_ALL_READ"
    }

    fun notifyMessage(
        userId: Long,
        message: Message,
    ): Mono<Void> {
        val event = mapOf(
            "type" to EventType.NEW_MESSAGE,
            "data" to message
        )
        return customWebSocketHandler.sendMessageToUser(userId, event)
    }

    fun isUserOnline(userId: Long): Boolean = customWebSocketHandler.isUserOnline(userId)


}
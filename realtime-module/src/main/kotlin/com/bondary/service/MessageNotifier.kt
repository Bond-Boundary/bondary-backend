package com.bondary.service

import com.bondary.model.Message
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class MessageNotifier(
    private val webSocketService: WebSocketService,
) {
    private val logger = LoggerFactory.getLogger(MessageNotifier::class.java)

    suspend fun notifyNewMessage(
        receiverId: Long,
        message: Message,
    ): Mono<Void> {
        return when (webSocketService.isUserOnline(receiverId)) {
            true -> sendWebSocketNotification(receiverId, message)
            false -> sendNotification(receiverId, message)
        }
    }

    private suspend fun sendWebSocketNotification(
        receiverId: Long,
        message: Message,
    ): Mono<Void> {
        return webSocketService.notifyMessage(message, receiverId)
            .doOnSuccess {
                logger.info("WebSocket 메시지 전송 성공: receiverId=$receiverId, messageId=${message.id}")
            }
            .doOnError { error ->
                logger.error("WebSocket 메시지 전송 실패: receiverId=$receiverId", error)
            }
            .then()
    }

    private suspend fun sendNotification(
        receiverId: Long,
        message: Message,
    ): Mono<Void> {
        logger.info("User $receiverId 오프라인: 푸시 알림 호출 필요 (senderId=${message.senderId}, content=${message.content})")
        // 푸시 알림 서비스 구현 후 아래 주석 해제
        // return notificationService.sendChatNotification(
        //     receiverId = receiverId,
        //     senderId = message.senderId,
        //     content = message.content,
        //     chatId = message.chatId
        // ).then()

        return Mono.empty()
    }
}

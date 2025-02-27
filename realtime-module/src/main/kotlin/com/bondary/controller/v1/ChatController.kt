package com.bondary.controller.v1

import com.bondary.controller.v1.request.MessageRequest
import com.bondary.controller.v1.response.MessageResponse
import com.bondary.service.ChatService
import kotlinx.coroutines.reactor.mono
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller

@Controller
class ChatController(
    private val chatService: ChatService,
    private val messagingTemplate: SimpMessagingTemplate
) {
    @MessageMapping("/chat.sendMessage")
    fun sendMessage(@Payload messageRequest: MessageRequest) = mono {
        val saved = chatService.processMessage(messageRequest.toDocument())
        val response = MessageResponse.of(saved)
        messagingTemplate.convertAndSendToUser(
            response.receiverId,
            "/queue/messages",
            response
        )
    }

}

package com.bondary.controller.v1

import com.bondary.controller.v1.response.MessageResponse
import com.bondary.service.MessageService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/messages")
class MessageController(
    private val messageService: MessageService
) {
    @GetMapping
    suspend fun findMessages(
        @RequestParam chatId: String,
        @RequestParam(defaultValue = "50") limit: Int,
        @RequestParam(required = false) lastMessageId: String?
    ): ResponseEntity<List<MessageResponse>> {
        val finds = messageService.findMessages(chatId, limit, lastMessageId)
        val response = MessageResponse.from(finds)
        return ResponseEntity.ok(response)
    }
}
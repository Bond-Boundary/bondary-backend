package com.bondary.controller.v1

import com.bondary.controller.v1.request.CreateChatRequest
import com.bondary.controller.v1.response.ChatResponse
import com.bondary.service.ChatService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/chats")
class ChatController(
    private val chatService: ChatService
) {
    @PostMapping
    suspend fun createChat(
        @RequestBody request: CreateChatRequest,
    ): ResponseEntity<ChatResponse> {
        val created = chatService.createChat(request.toDocument())
        return ResponseEntity.status(HttpStatus.OK)
            .body(ChatResponse.from(created))
    }





}
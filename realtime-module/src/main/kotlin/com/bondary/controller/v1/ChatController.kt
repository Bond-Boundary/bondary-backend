package com.bondary.controller.v1

import com.bondary.controller.v1.request.CreateChatRequest
import com.bondary.controller.v1.response.ChatItemResponse
import com.bondary.controller.v1.response.ChatResponse
import com.bondary.service.ChatService
import com.bondary.service.UserChatService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/chats")
class ChatController(
    private val chatService: ChatService,
    private val userChatService: UserChatService,
) {
    @PostMapping
    suspend fun createChat(
        @RequestBody request: CreateChatRequest,
    ): ResponseEntity<ChatResponse> {
        val created = chatService.createChat(request.toDocument())
        val response = ChatResponse.from(created)
        return ResponseEntity.ok(response)
    }

    @GetMapping
    suspend fun findChats(
        @RequestParam userId: Long, // 임시, 인증 구현 전까지 test 용도 -> 추후 ArgumentResolver 로 변경
    ): ResponseEntity<List<ChatItemResponse>> {
        val finds = userChatService.findUserChats(userId)
        val response = ChatItemResponse.from(finds).sortedByDescending { it.displayIdx }
        return ResponseEntity.ok(response)
    }
}

package com.bondary.controller

import com.bondary.dto.DefaultIdResponse
import com.bondary.dto.UserChatRequest
import com.bondary.service.ChatService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/chats")
class ChatRoomController(
    private val chatService: ChatService,
) {
    @PostMapping
    fun createChat(
        @RequestBody request: UserChatRequest,
    ): Mono<DefaultIdResponse> {
        val successId = chatService.createOrGetChatRoom(request.title, request.thumbnailId, request.userIds)
        return DefaultIdResponse.of(successId)
    }
}

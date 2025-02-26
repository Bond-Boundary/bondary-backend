package com.bondary.domain.chat

import com.bondary.support.DefaultIdResponse
import com.bondary.support.SliceResult
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/chats")
class ChatController(
    private val chatService: ChatService
) {
    @PostMapping
    suspend fun appendChat(
        @RequestBody request: CreateChatRequest
    ): ResponseEntity<DefaultIdResponse> {
        val successId = chatService.createChat(request)
        return ResponseEntity.ok(DefaultIdResponse(successId))
    }

    @GetMapping
    suspend fun findChats(
        @RequestParam userId: Long,
        @RequestParam key: String?,
        @RequestParam(defaultValue = "20") size: Int
    ): ResponseEntity<SliceResult<ChatResponse>> {
        val chats = chatService.findChats(userId, key, size)
        return ResponseEntity.ok(chats)
    }

    @DeleteMapping("/{chatId}/leave")
    suspend fun leaveChat(
        @PathVariable chatId: String,
        @RequestParam userId: Long
    ): ResponseEntity<DefaultIdResponse> {
        chatService.leaveChat(chatId, userId)
        return ResponseEntity.ok().build()
    }

    @PutMapping("/{chatId}/mute")
    suspend fun muteChat(
        @PathVariable chatId: String,
        @RequestParam userId: Long,
        @RequestParam mute: Boolean
    ): ResponseEntity<Unit> {
        chatService.muteChat(chatId, userId, mute)
        return ResponseEntity.ok().build()
    }
}

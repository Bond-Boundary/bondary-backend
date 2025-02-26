package com.bondary.domain.message

import com.bondary.support.SliceResult
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/messages")
class MessageController(
    private val messageService: MessageService
) {
    @GetMapping("/{chatId}/unread/count")
    suspend fun getUnreadMessageCount(
        @PathVariable chatId: String,
        @RequestParam userId: Long
    ): ResponseEntity<UnreadMessageCountResponse> {
        val count = messageService.countUnreadMessages(chatId, userId)
        return ResponseEntity.ok(UnreadMessageCountResponse(count))
    }

    @PutMapping("/{chatId}/read-all")
    suspend fun markMessagesAsRead(
        @PathVariable chatId: String,
        @RequestParam userId: Long
    ): ResponseEntity<Unit> {
        messageService.markMessagesAsRead(
            chatId = chatId,
            userId = userId
        )
        return ResponseEntity.ok().build()
    }

    @GetMapping("/{chatId}")
    suspend fun findMessages(
        @PathVariable chatId: String,
        @RequestParam key: String?, // 마지막으로 받은 메시지의 ID
        @RequestParam(defaultValue = "20") size: Int
    ): ResponseEntity<SliceResult<MessageResponse>> {
        val messages = messageService.findMessages(chatId, key, size)
        return ResponseEntity.ok(messages)
    }
}

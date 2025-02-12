package com.bondary.controller

import com.bondary.dto.MessageResponse
import com.bondary.service.MessageService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/messages")
class MessageController(
    private val messageService: MessageService
) {
    // 특정 유저와의 1:1 채팅 메시지 조회
    @GetMapping("/{userId}/{receiverId}")
    suspend fun getMessages(
        @PathVariable userId: Long,
        @PathVariable receiverId: Long
    ): List<MessageResponse> {
        val messages = messageService.getMessagesBetweenUsers(userId, receiverId)
        return MessageResponse.of(messages)
    }

    // 특정 유저와의 1:1 채팅에서 안 읽은 메시지 개수 조회
    @GetMapping("/{userId}/{receiverId}/unread-count")
    suspend fun getUnreadMessagesCount(
        @PathVariable userId: Long,
        @PathVariable receiverId: Long
    ): Long {
        val unreadMessagesCount = messageService.getUnreadMessagesCount(userId, receiverId)
        return unreadMessagesCount
    }

    // 특정 유저와의 1:1 채팅 메시지 읽음 처리
    @PutMapping("/{userId}/{receiverId}/read")
    suspend fun markMessageAsRead(
        @PathVariable userId: Long,
        @PathVariable receiverId: Long
    ): String {
        messageService.markMessagesAsRead(userId, receiverId)
        return "메시지를 성공적으로 읽었습니다."
    }
}

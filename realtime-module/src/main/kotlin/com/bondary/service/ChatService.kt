package com.bondary.service

import com.bondary.model.Message
import com.bondary.repository.MessageRepository
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service

@Service
class ChatService(
    private val messageRepository: MessageRepository
) {
    /**
     * 메시지 처리 로직
     * - DB에 메시지 저장 (ex: MongoDB bulkWrite)
     * - 추가 알림 처리 등 비즈니스 로직 수행
     */
    suspend fun processMessage(message: Message) : Message{
        return messageRepository.save(message).awaitSingle()
    }
}

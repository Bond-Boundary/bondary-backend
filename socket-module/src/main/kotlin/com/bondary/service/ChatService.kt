package com.bondary.service

import com.bondary.entity.Chat
import com.bondary.repository.ChatRepository
import org.springframework.stereotype.Service

@Service
class ChatService(
    private val chatRepository: ChatRepository
) {
    suspend fun createOrGetChatRoom(
        title: String,
        thumbnailId: Long,
        userIds: List<Long>
    ): String {
        val sortedUserIds = userIds.sorted()
        val existingChat = chatRepository.findByUserIds(sortedUserIds)

        return existingChat?.id ?: chatRepository.save(
            Chat(
                title = title,
                thumbnailId = thumbnailId,
                userIds = sortedUserIds
            )
        ).id!!
    }
}

package com.bondary.service

import com.bondary.entity.Chat
import com.bondary.repository.ChatRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ChatService(
    private val chatRepository: ChatRepository,
) {
    fun createOrGetChatRoom(
        title: String,
        thumbnailId: Long,
        userIds: List<Long>,
    ): Mono<String> {
        val sortedUserIds = userIds.sorted()

        return chatRepository.findByUserIdsContaining(sortedUserIds[0])
            .filter { it.userIds.containsAll(sortedUserIds) }
            .next()
            .map { it.id!! }
            .switchIfEmpty(
                chatRepository.save(
                    Chat(
                        title = title,
                        thumbnailId = thumbnailId,
                        userIds = sortedUserIds,
                    ),
                ).map { it.id!! },
            )
    }
}

package com.bondary.repository

import com.bondary.entity.Chat
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface ChatRepository : ReactiveMongoRepository<Chat, String> {
    fun findByUserIdsContaining(userId: Long): Flux<Chat>
}

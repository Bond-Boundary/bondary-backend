package com.bondary.repository

import com.bondary.model.Chat
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class ChatRepositoryCustomImpl(
    private val reactiveMongoTemplate: ReactiveMongoTemplate,
) : ChatRepositoryCustom {
    override fun findByParticipants(userId: Long): Flux<Chat> {
        val query = Query(Criteria.where("participants").`in`(userId))
        return reactiveMongoTemplate.find(query, Chat::class.java)
    }

    override fun modifyLastMessage(
        chatId: String,
        lastMessage: String,
    ): Mono<Boolean> {
        val query = Query(Criteria.where("_id").`is`(chatId))
        val update = Update().set("lastMessage", lastMessage)
        return reactiveMongoTemplate.updateFirst(query, update, Chat::class.java)
            .map { it.modifiedCount > 0 }
    }
}

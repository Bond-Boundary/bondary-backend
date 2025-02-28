package com.bondary.repository

import com.bondary.model.Message
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class MessageRepositoryCustomImpl(
    private val reactiveMongoTemplate: ReactiveMongoTemplate
) : MessageRepositoryCustom {

    override fun findByChatId(chatId: String, limit: Int): Flux<Message> {
        val query = Query(Criteria.where("chatId").`is`(chatId))
            .with(Sort.by(Sort.Direction.DESC, "timestamp"))
            .limit(limit)
        return reactiveMongoTemplate.find(query, Message::class.java)
    }

    override fun markAsRead(messageId: String): Mono<Boolean> {
        val query = Query(Criteria.where("_id").`is`(messageId))
        val update = Update().set("isRead", true)
        return reactiveMongoTemplate.updateFirst(query, update, Message::class.java)
            .map { it.modifiedCount > 0 }
    }
}

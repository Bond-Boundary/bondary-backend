package com.bondary.repository

import com.bondary.model.UserChat
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class UserChatRepositoryCustomImpl(
    private val reactiveMongoTemplate: ReactiveMongoTemplate
) : UserChatRepositoryCustom {

    override fun findByUserIdAndChatId(userId: Long, chatId: String): Mono<UserChat> {
        val query = Query(Criteria.where("userId").`is`(userId)
            .and("chatId").`is`(chatId))
        return reactiveMongoTemplate.findOne(query, UserChat::class.java)
    }

    override fun findByUserId(userId: Long): Flux<UserChat> {
        val query = Query(Criteria.where("userId").`is`(userId))
        return reactiveMongoTemplate.find(query, UserChat::class.java)
    }

    override fun incrementUnreadCount(userId: Long, chatId: String): Mono<Boolean> {
        val query = Query(Criteria.where("userId").`is`(userId)
            .and("chatId").`is`(chatId))
        val update = Update().inc("unreadCount", 1)
        return reactiveMongoTemplate.updateFirst(query, update, UserChat::class.java)
            .map { it.modifiedCount > 0 }
    }

    override fun resetUnreadCount(userId: Long, chatId: String): Mono<Boolean> {
        val query = Query(Criteria.where("userId").`is`(userId)
            .and("chatId").`is`(chatId))
        val update = Update().set("unreadCount", 0)
        return reactiveMongoTemplate.updateFirst(query, update, UserChat::class.java)
            .map { it.modifiedCount > 0 }
    }

    override fun updateDisplayIndex(userId: Long, chatId: String, displayIndex: String): Mono<Boolean> {
        val query = Query(Criteria.where("userId").`is`(userId)
            .and("chatId").`is`(chatId))
        val update = Update().set("displayIndex", displayIndex)
        return reactiveMongoTemplate.updateFirst(query, update, UserChat::class.java)
            .map { it.modifiedCount > 0 }
    }
}

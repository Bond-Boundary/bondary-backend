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
    private val reactiveMongoTemplate: ReactiveMongoTemplate,
) : MessageRepositoryCustom {
    override fun findByChatId(
        chatId: String,
        limit: Int,
        lastMessageId: String?,
    ): Flux<Message> {
        val criteria = Criteria.where("chatId").`is`(chatId)
        if (lastMessageId != null) {
            criteria.and("_id").lt(lastMessageId)
        }
        val query = Query(criteria)
            .with(Sort.by(Sort.Direction.DESC, "timestamp"))
            .limit(limit)
        return reactiveMongoTemplate.find(query, Message::class.java)
    }

    override fun markMessageAsRead(messageId: String): Mono<Boolean> {
        val query = Query(Criteria.where("_id").`is`(messageId))
        val update = Update().set("isRead", true)
        return reactiveMongoTemplate.updateFirst(query, update, Message::class.java)
            .map { it.modifiedCount > 0 }
    }

    /**
     * 만약 채팅방에 여러 사용자가 있다면(추후 그룹 채팅방),
     * 각 사용자가 읽은 메시지와 읽지 않은 메시지가 다를 수 있으므로,
     * “receiverId”가 해당 사용자인 메시지만 업데이트!!!
     */
    override fun markAllMessageAsRead(
        chatId: String,
        userId: Long,
    ): Mono<Boolean> {
        val query = Query(
            Criteria.where("chatId").`is`(chatId)
                .and("receiverId").`is`(userId)
                .and("isRead").`is`(false),
        )
        val update = Update().set("isRead", true)
        return reactiveMongoTemplate.updateMulti(query, update, Message::class.java)
            .map { it.modifiedCount > 0 }
    }
}

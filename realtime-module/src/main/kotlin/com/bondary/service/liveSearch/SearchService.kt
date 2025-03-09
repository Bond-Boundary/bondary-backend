package com.bondary.service.liveSearch

import com.bondary.model.User
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.TextCriteria
import org.springframework.data.mongodb.core.query.TextQuery
import org.springframework.stereotype.Service

@Service
class SearchService(
    private val reactiveMongoTemplate: ReactiveMongoTemplate
) {
    suspend fun searchUsers(
        name: String,
        limit: Int
    ): List<User> = coroutineScope {
        // 정규식을 사용한 검색 (case-insensitive)
        val criteria = Criteria.where("name").regex(".*$name.*", "i")
        val query = Query(criteria).limit(limit)

        // 비동기적으로 검색 실행
        reactiveMongoTemplate.find(query, User::class.java)
            .asFlow()
            .toList()

        // 텍스트 인덱스를 사용한 고급 검색의 경우 (MongoDB에 텍스트 인덱스가 설정되어 있어야 함)
        // val textCriteria = TextCriteria.forDefaultLanguage().matching(name)
        // val textQuery = TextQuery.queryText(textCriteria).sortByScore().limit(limit)
        // reactiveMongoTemplate.find(textQuery, User::class.java)
        //     .asFlow()
        //     .toList()
    }

    // 실시간 스트리밍 검색 - 웹소켓이나 SSE로 클라이언트에 제공할 수 있음
    fun searchUsersReactive(name: String, limit: Int): Flow<User> {
        val criteria = Criteria.where("name").regex(".*$name.*", "i")
        val query = Query(criteria).limit(limit)

        return reactiveMongoTemplate.find(query, User::class.java).asFlow()
    }
}
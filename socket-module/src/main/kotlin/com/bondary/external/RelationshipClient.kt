package com.bondary.external

import com.bondary.domain.chat.DummyRelationshipResponse
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class RelationshipClient(
    private val webClient: WebClient
) {
//    suspend fun checkRelationship(
//        userId: Long,
//        targetId: Long
//    ): RelationshipResponse {
//        return webClient.get()
//            .uri(
//                "/api/v1/relationships/check?userId={userId}&targetId={targetId}",
//                userId,
//                targetId,
//            )
//            .retrieve()
//            .awaitBody()
//    }

    suspend fun checkRelationship(userId: Long, targetId: Long): DummyRelationshipResponse {
        println("관계 확인 API - 임시 더미 응답 반환")
        return DummyRelationshipResponse("FRIEND") // 임시로 FRIEND 반환
    }
}

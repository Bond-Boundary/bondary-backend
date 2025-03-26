package com.bondary.controller.v1.liveSearch

import com.bondary.controller.v1.liveSearch.res.SearchResponse
import com.bondary.model.User
import com.bondary.service.liveSearch.SearchService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

@RestController
@RequestMapping("/v1/search")
class SearchController(
    private val searchService: SearchService,
) {
    /**
     * 일반 검색 엔드포인트
     */
    @GetMapping
    fun search(
        @RequestParam name: String,
        @RequestParam(defaultValue = "3") limit: Int,
    ): Mono<List<SearchResponse>> {
        return searchService.searchUsers(name, limit)
            .map { users -> users.map { user -> SearchResponse.fromUser(user) } }
    }

    /**
     * Server-Sent Events(SSE)를 사용한 실시간 검색 엔드포인트
     */
    @GetMapping("/stream", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun streamSearch(
        @RequestParam name: String,
        @RequestParam(defaultValue = "3") limit: Int
    ): Flux<SearchResponse> {
        return searchService.searchUsersReactive(name, limit)
            .map { user -> SearchResponse.fromUser(user) }
            // 연결을 유지하기 위한 빈 이벤트(30초마다)
            .mergeWith(Flux.interval(Duration.ofSeconds(30)).map { null })
            .takeWhile { it != null }
    }
}
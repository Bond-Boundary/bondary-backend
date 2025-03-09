package com.bondary.controller.v1.liveSearch

import com.bondary.controller.v1.liveSearch.res.SearchResponse
import com.bondary.model.User
import com.bondary.service.liveSearch.SearchService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/search")
class SearchController(
    private val searchService: SearchService,
) {
    @GetMapping
    suspend fun search(
        @RequestParam name: String,
        @RequestParam(defaultValue = "3") limit: Int,
    ) : ResponseEntity<List<SearchResponse>>{
        val finds = searchService.searchUsers(name, limit)
        val response = finds.map { user -> SearchResponse.fromUser(user) }
        return ResponseEntity.ok(response)
    }

    // SSE를 통한 실시간 검색 엔드포인트
    @GetMapping("/stream", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun streamSearch(
        @RequestParam name: String,
        @RequestParam(defaultValue = "3") limit: Int
    ): Flow<SearchResponse> {
        return searchService.searchUsersReactive(name, limit)
            .map { user: User -> SearchResponse.fromUser(user) }
    }
}
package com.bondary.controller.v1.liveSearch.res

import com.bondary.model.User
import com.bondary.persistence.jpa.member.entity.InterestArea

/**
 * 검색 결과 응답 모델
 */
data class SearchResponse(
    val id: String,
    val name: String,
    val profileImage: String,
    val introduction: String,
    val schoolName: String,
    val firstMajorName: String,
    val secondaryMajorName: String?,
    val interestAreas: List<InterestArea>
) {
    companion object {
        /**
         * User 도메인 모델을 SearchResponse로 변환
         */
        fun fromUser(user: User): SearchResponse {
            return SearchResponse(
                id = user.id,
                name = user.name,
                profileImage = user.profileImage,
                introduction = user.introduction,
                schoolName = user.schoolName,
                firstMajorName = user.firstMajorName,
                secondaryMajorName = user.secondaryMajorName,
                interestAreas = user.interestArea
            )
        }
    }
}
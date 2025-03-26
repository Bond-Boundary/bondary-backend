package com.bondary.model

import com.bondary.persistence.jpa.member.entity.InterestArea
import java.time.LocalDateTime

/**
 * 사용자 도메인 모델
 * MemberEntity와 매핑되는 도메인 객체
 */
data class User(
    val id: String,
    val name: String,
    val profileImage: String,
    val introduction: String,
    val schoolName: String,
    val firstMajorName: String,
    val secondaryMajorName: String?,
    val interestArea: List<InterestArea>,
    val interestJob: String?,
    val instagram: String?,
    val linkedin: String?,
    val etcLinks: List<String>?,
    val onBoardingAt: LocalDateTime?
)
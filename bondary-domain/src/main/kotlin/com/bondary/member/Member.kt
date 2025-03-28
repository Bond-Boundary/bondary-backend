package com.bondary.member

import com.bondary.support.AggregateDomain
import com.bondary.support.DomainId
import java.time.LocalDateTime

class Member(
    id: DomainId,
    var name: String,
    var profileImage: String,
    var introduction: String,
    var schoolName: String,
    var firstMajorName: String,
    var secondaryMajorName: String?,
    var instagram: String?,
    var linkedin: String?,
    var interestArea: List<String>,
    var interestJob: String?,
    var etcLinks: List<String>?,
    var onboardingAt: LocalDateTime?,
    createdAt: LocalDateTime,
    updatedAt: LocalDateTime,
) : AggregateDomain<Member>(id, createdAt, updatedAt) {
    companion object {
        fun append(
            id: DomainId = DomainId.generate(),
            name: String,
            profileImage: String,
            introduction: String,
            schoolName: String,
            firstMajorName: String,
            secondaryMajorName: String?,
            instagram: String?,
            linkedin: String?,
            interestArea: List<String>,
            interestJob: String?,
            etcLinks: List<String>?,
            onboardingAt: LocalDateTime = LocalDateTime.now(),
            createdAt: LocalDateTime = LocalDateTime.now(),
            updatedAt: LocalDateTime = LocalDateTime.now(),
        ): Member = Member(
            id = id,
            name = name,
            profileImage = profileImage,
            introduction = introduction,
            schoolName = schoolName,
            firstMajorName = firstMajorName,
            secondaryMajorName = secondaryMajorName,
            interestArea = interestArea,
            interestJob = interestJob,
            instagram = instagram,
            linkedin = linkedin,
            etcLinks = etcLinks,
            onboardingAt = onboardingAt,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    fun isOnboarding(): Boolean {
        return this.onboardingAt != null
    }
}
package com.bondary.persistence.jpa.member.mapper

import com.bondary.member.Member
import com.bondary.persistence.jpa.member.entity.InterestArea
import com.bondary.persistence.jpa.member.entity.MemberEntity
import com.bondary.support.DomainId

object MemberMapper {
    fun toMemberEntity(member: Member): MemberEntity =
        MemberEntity(
            id = member.id.value,
            name = member.name,
            profileImage = member.profileImage,
            introduction = member.introduction,
            schoolName = member.schoolName,
            firstMajorName = member.firstMajorName,
            secondaryMajorName = member.secondaryMajorName,
            interestArea = member.interestArea.map { InterestArea.valueOf(it) },
            interestJob = member.interestJob,
            instagram = member.instagram,
            linkedin = member.linkedin,
            etcLinks = member.etcLinks,
            onBoardingAt = member.onboardingAt
        )

    fun toMemberDomain(memberEntity: MemberEntity): Member =
        Member(
            id = DomainId(memberEntity.id),
            name = memberEntity.name,
            profileImage = memberEntity.profileImage,
            introduction = memberEntity.introduction,
            schoolName = memberEntity.schoolName,
            firstMajorName = memberEntity.firstMajorName,
            secondaryMajorName = memberEntity.secondaryMajorName,
            interestArea = memberEntity.interestArea.map { it.name },
            interestJob = memberEntity.interestJob,
            instagram = memberEntity.instagram,
            linkedin = memberEntity.linkedin,
            etcLinks = memberEntity.etcLinks,
            onboardingAt = memberEntity.onBoardingAt,
            createdAt = memberEntity.createdAt,
            updatedAt = memberEntity.updatedAt
        )
}
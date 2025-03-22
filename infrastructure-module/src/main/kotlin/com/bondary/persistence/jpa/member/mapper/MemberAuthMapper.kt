package com.bondary.persistence.jpa.member.mapper

import com.bondary.member.MemberAuth
import com.bondary.member.MemberToken
import com.bondary.member.SocialId
import com.bondary.persistence.jpa.member.entity.MemberAuthEntity
import com.bondary.persistence.jpa.member.entity.MemberTokenEntity
import com.bondary.support.DomainId

object MemberAuthMapper {
    fun toMemberAuthEntity(memberAuth: MemberAuth) : MemberAuthEntity =
        MemberAuthEntity(
            id = memberAuth.id.value,
            memberId = memberAuth.memberId!!.value,
            socialId = memberAuth.socialId.value,
            oAuthProvider = memberAuth.oAuthProvider
        )

    fun toMemberAuthDomain(memberAuthEntity: MemberAuthEntity): MemberAuth =
        MemberAuth(
            id = DomainId(memberAuthEntity.id),
            memberId = DomainId(memberAuthEntity.memberId),
            socialId = SocialId(memberAuthEntity.socialId),
            oAuthProvider = memberAuthEntity.oAuthProvider,
            createdAt = memberAuthEntity.createdAt,
            updatedAt = memberAuthEntity.updatedAt
        )

    fun toMemberTokenEntity(memberToken: MemberToken) : MemberTokenEntity =
        MemberTokenEntity(
            id = memberToken.id.value,
            memberId = memberToken.memberId?.value,
            token = memberToken.token
        )

    fun toMemberTokenDomain(memberTokenEntity: MemberTokenEntity): MemberToken =
        MemberToken(
            id = DomainId(memberTokenEntity.id),
            memberId = memberTokenEntity.memberId?.let { DomainId(it) },
            token = memberTokenEntity.token,
            createdAt = memberTokenEntity.createdAt,
            updatedAt = memberTokenEntity.updatedAt
        )
}
package com.bondary.member

import com.bondary.OAuthProvider
import com.bondary.SocialId
import com.bondary.support.BaseDomain
import com.bondary.support.DomainId
import java.time.LocalDateTime

class MemberAuth(
    id: DomainId,
    val memberId: DomainId?,
    var socialId: SocialId,
    val oAuthProvider: OAuthProvider,
    createdAt: LocalDateTime,
    updatedAt: LocalDateTime,
) : BaseDomain(id, createdAt, updatedAt) {
    companion object{
        fun append(
            memberId: DomainId? = null,
            socialId: SocialId,
            oAuthProvider: OAuthProvider,
            createdAt: LocalDateTime = LocalDateTime.now(),
            updatedAt: LocalDateTime = LocalDateTime.now(),
        ): MemberAuth =
            MemberAuth(
                id = DomainId.generate(),
                memberId = memberId,
                socialId = socialId,
                oAuthProvider = oAuthProvider,
                createdAt = createdAt,
                updatedAt = updatedAt
            )
    }

}

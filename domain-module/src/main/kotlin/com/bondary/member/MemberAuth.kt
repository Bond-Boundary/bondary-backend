package com.bondary.member

import com.bondary.support.BaseDomain
import com.bondary.support.DomainId
import java.time.LocalDateTime

class MemberAuth(
    id: DomainId,
    val memberId: DomainId,
    var socialId: SocialId,
    val oauthProvider: OAuthProvider,
    createdAt: LocalDateTime,
    updatedAt: LocalDateTime,
) : BaseDomain(id, createdAt, updatedAt) {
    companion object{
        fun append(
            memberId: DomainId,
            socialId: SocialId,
            oauthProvider: OAuthProvider,
            createdAt: LocalDateTime = LocalDateTime.now(),
            updatedAt: LocalDateTime = LocalDateTime.now(),
        ): MemberAuth =
            MemberAuth(
                id = DomainId.generate(),
                memberId = memberId,
                socialId = socialId,
                oauthProvider = oauthProvider,
                createdAt = createdAt,
                updatedAt = updatedAt
            )
    }

}

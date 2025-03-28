package com.bondary.member

import com.bondary.support.BaseDomain
import com.bondary.support.DomainId
import java.time.LocalDateTime

class MemberToken(
    id: DomainId,
    val memberId: DomainId?,
    val token: String,
    createdAt: LocalDateTime,
    updatedAt: LocalDateTime,
) : BaseDomain(id, createdAt, updatedAt) {
    companion object {
        fun append(
            memberId: DomainId? = null,
            token: String,
            createdAt: LocalDateTime = LocalDateTime.now(),
            updatedAt: LocalDateTime = LocalDateTime.now(),
        ): MemberToken =
            MemberToken(
                id = DomainId.generate(),
                memberId = memberId,
                token = token,
                createdAt = createdAt,
                updatedAt = updatedAt
            )
    }
}

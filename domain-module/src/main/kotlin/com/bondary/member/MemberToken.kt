package com.bondary.member

import com.bondary.support.DomainId

class MemberToken(
    id: DomainId,
    val memberId: DomainId?,
    val token: String
) {
    companion object {
        fun append(
            memberId: DomainId? = null,
            token: String
        ): MemberToken =
            MemberToken(
                id = DomainId.generate(),
                memberId = memberId,
                token = token
            )
    }
}

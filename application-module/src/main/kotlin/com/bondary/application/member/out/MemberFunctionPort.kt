package com.bondary.application.member.out

import com.bondary.member.Member
import com.bondary.support.DomainId

interface MemberFunctionPort {
    suspend fun getMember(memberId: DomainId): Member?

    suspend fun save(member: Member): String
}
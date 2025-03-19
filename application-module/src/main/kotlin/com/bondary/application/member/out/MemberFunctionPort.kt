package com.bondary.application.member.out

import com.bondary.member.Member
import com.bondary.support.DomainId

interface MemberFunctionPort {
    fun getMember(memberId: DomainId): Member?

    fun save(member: Member): Member
}
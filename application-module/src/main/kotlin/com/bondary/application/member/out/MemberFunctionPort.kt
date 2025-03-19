package com.bondary.application.member.out

import com.bondary.member.Member
import com.bondary.support.DomainId

interface MemberFunctionPort {
    fun getMember(userId: DomainId): Member?
}
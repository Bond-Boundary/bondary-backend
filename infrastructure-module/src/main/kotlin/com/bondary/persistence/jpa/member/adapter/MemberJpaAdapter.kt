package com.bondary.persistence.jpa.member.adapter

import com.bondary.application.member.out.MemberFunctionPort
import com.bondary.member.Member
import com.bondary.persistence.jpa.member.mapper.MemberMapper
import com.bondary.persistence.jpa.member.repository.MemberJpaRepository
import com.bondary.support.CoreException
import com.bondary.support.DomainId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class MemberJpaAdapter(
    private val memberJpaRepository: MemberJpaRepository
) : MemberFunctionPort {
    override suspend fun save(member: Member): String {
        val memberEntity = MemberMapper.toMemberEntity(member)
        withContext(Dispatchers.IO) {
            memberJpaRepository.save(memberEntity)
        }
        return memberEntity.id
    }

    override suspend fun getMember(memberId: DomainId): Member? {
        return memberJpaRepository.findByIdOrNull(memberId.value)
            ?.let {MemberMapper.toMemberDomain(it)}
            ?: throw CoreException.NotFoundData("멤버를 찾을 수 없습니다.")
    }
}
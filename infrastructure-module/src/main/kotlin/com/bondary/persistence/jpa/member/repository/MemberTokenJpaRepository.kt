package com.bondary.persistence.jpa.member.repository

import com.bondary.persistence.jpa.member.entity.MemberTokenEntity
import com.bondary.support.DomainId
import org.springframework.data.jpa.repository.JpaRepository

interface MemberTokenJpaRepository : JpaRepository<MemberTokenEntity, String> {
    fun existsByToken(token: String): Boolean

    fun existsByTokenAndMemberId(token: String, memberId: String): Boolean

    fun deleteByToken(refreshToken: String)
}
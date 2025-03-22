package com.bondary.persistence.jpa.member.adapter

import com.bondary.application.member.out.MemberAuthInfoPort
import com.bondary.member.MemberAuth
import com.bondary.member.OAuthProvider
import com.bondary.member.SocialId
import com.bondary.persistence.jpa.member.mapper.MemberAuthMapper
import com.bondary.persistence.jpa.member.repository.MemberAuthJpaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Repository

@Repository
class MemberAuthJpaAdapter(
    private val memberAuthJpaRepository: MemberAuthJpaRepository
) : MemberAuthInfoPort {
    override suspend fun getMemberAuthInfo(socialId: SocialId, oauthProvider: OAuthProvider): MemberAuth? =
        withContext(Dispatchers.IO) {
            memberAuthJpaRepository.findBySocialIdAndOAuthProvider(socialId.value, oauthProvider)
        }?.let { MemberAuthMapper.toMemberAuthDomain(it) }

    override suspend fun isExistsMemberAuthInfo(socialId: SocialId, oAuthProvider: OAuthProvider): Boolean =
        withContext(Dispatchers.IO) {
            memberAuthJpaRepository.existsBySocialIdAndOAuthProvider(
                socialId = socialId.value,
                oAuthProvider = oAuthProvider
            )
        }

    override suspend fun saveMemberAuthInfo(memberAuth: MemberAuth): MemberAuth =
        withContext(Dispatchers.IO) {
            val memberAuthEntity = MemberAuthMapper.toMemberAuthEntity(memberAuth)
            val savedEntity = memberAuthJpaRepository.save(memberAuthEntity)
            MemberAuthMapper.toMemberAuthDomain(savedEntity)
        }
}
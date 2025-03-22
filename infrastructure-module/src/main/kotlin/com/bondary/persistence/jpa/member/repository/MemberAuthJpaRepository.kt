package com.bondary.persistence.jpa.member.repository

import com.bondary.member.OAuthProvider
import com.bondary.persistence.jpa.member.entity.MemberAuthEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MemberAuthJpaRepository : JpaRepository<MemberAuthEntity, String> {
    fun existsBySocialIdAndOAuthProvider(socialId: String, oAuthProvider: OAuthProvider): Boolean

    fun findBySocialIdAndOAuthProvider(socialId: String, oauthProvider: OAuthProvider): MemberAuthEntity?
}
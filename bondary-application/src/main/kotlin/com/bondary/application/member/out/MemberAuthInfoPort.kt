package com.bondary.application.member.out

import com.bondary.member.MemberAuth
import com.bondary.OAuthProvider
import com.bondary.SocialId

interface MemberAuthInfoPort {
    suspend fun getMemberAuthInfo(
        socialId: SocialId,
        oauthProvider: OAuthProvider
    ): MemberAuth?

    suspend fun isExistsMemberAuthInfo(
        socialId: SocialId,
        oAuthProvider: OAuthProvider
    ): Boolean

    suspend fun saveMemberAuthInfo(memberAuth: MemberAuth): MemberAuth
}
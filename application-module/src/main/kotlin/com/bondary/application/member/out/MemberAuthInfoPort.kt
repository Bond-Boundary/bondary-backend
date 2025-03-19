package com.bondary.application.member.out

import com.bondary.member.MemberAuth
import com.bondary.member.OAuthProvider
import com.bondary.member.SocialId

interface MemberAuthInfoPort {
    fun getMemberAuthInfo(
        socialId: SocialId,
        oauthProvider: OAuthProvider
    ): MemberAuth?

    fun isExistsMemberAuthInfo(
        socialId: SocialId,
        oAuthProvider: OAuthProvider
    ): Boolean

    fun saveMemberAuthInfo(memberAuth: MemberAuth): MemberAuth
}
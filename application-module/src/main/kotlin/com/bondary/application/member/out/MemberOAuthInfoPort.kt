package com.bondary.application.member.out

import com.bondary.application.member.OAuthMemberInfo
import com.bondary.member.OAuthProvider

interface MemberOAuthInfoPort {
    fun getAuthInfo(
        provider: OAuthProvider,
        accessToken: String
    ): OAuthMemberInfo
}
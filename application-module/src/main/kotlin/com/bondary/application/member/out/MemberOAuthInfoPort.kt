package com.bondary.application.member.out

import com.bondary.application.member.OAuthMemberInfo
import com.bondary.member.OAuthProvider

interface MemberOAuthInfoPort {
    fun getOAuthInfo(
        oauthProvider: OAuthProvider,
        accessToken: String
    ): OAuthMemberInfo
}
package com.bondary.application.member.out

import com.bondary.application.member.OAuthMemberInfo
import com.bondary.OAuthProvider

interface MemberOAuthInfoPort {
    suspend fun getOAuthMemberInfo(
        oauthProvider: OAuthProvider,
        accessToken: String
    ): OAuthMemberInfo
}
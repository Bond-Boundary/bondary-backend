package com.bondary.application.member.out

import com.bondary.member.OAuthProvider

interface MemberOAuthCodeExchangePort {
    suspend fun exchangeAuthCodeForToken(
        provider: OAuthProvider,
        authorizationCode: String,
        redirectUri: String
    ): String
}
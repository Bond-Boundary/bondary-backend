package com.bondary.application.member.out

import com.bondary.OAuthProvider

interface MemberOAuthCodeExchangePort {
    suspend fun exchangeAuthCodeForToken(
        provider: OAuthProvider,
        authorizationCode: String,
        redirectUri: String
    ): String
}
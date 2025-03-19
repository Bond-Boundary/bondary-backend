package com.bondary.application.member

import com.bondary.member.OAuthProvider
import com.bondary.member.SocialId

sealed class TokenType {
    data class RegisterToken(
        val socialId: SocialId,
        val oAuthProvider: OAuthProvider,
        val name: String,
        val email: String,
        val profileImage: String,
    ) : TokenType()

    data class AccessToken(
        val memberId: String,
    ) : TokenType()

    data class RefreshToken(
        val memberId: String,
    ) : TokenType()
}
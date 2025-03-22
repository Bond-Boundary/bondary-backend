package com.bondary.application.member.out

import com.bondary.application.member.TokenType
import com.bondary.member.Member
import com.bondary.member.MemberToken
import com.bondary.support.DomainId

interface MemberTokenPort {
    fun generateRegisterToken(
        name: String,
        email: String,
        profileImage: String,
        socialId: String,
        oAuthProvider: String
    ): String

    fun generateAccessToken(member: Member): String

    fun generateRefreshToken(member: Member): String

    fun saveToken(memberToken: MemberToken)

    fun isExistTokenByToken(token: String): Boolean

    fun isExistTokenByTokenAndDomainId(token: String, memberId: DomainId): Boolean

    fun resolveRegisterToken(token: String): TokenType.RegisterToken

    fun resolveAccessToken(token: String): TokenType.AccessToken

    fun resolveRefreshToken(token: String): TokenType.RefreshToken

    fun deleteToken(refreshToken: String)
}
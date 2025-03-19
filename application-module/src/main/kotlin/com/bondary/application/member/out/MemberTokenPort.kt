package com.bondary.application.member.out

import com.bondary.application.member.TokenType
import com.bondary.member.Member
import com.bondary.member.MemberToken
import com.bondary.member.SocialId

interface MemberTokenPort {
    fun generateRegisterToken(
        name: String,
        email: String,
        profileImage: String,
        socialId: SocialId,
        oAuthProvider: String
    ): String

    fun generateAccessToken(member: Member?): String

    fun generateRefreshToken(member: Member?): String

    fun appendToken(token: MemberToken)

    fun isExistToken(token: String): Boolean

    fun resolveRegisterToken(token: String): TokenType.RegisterToken
}
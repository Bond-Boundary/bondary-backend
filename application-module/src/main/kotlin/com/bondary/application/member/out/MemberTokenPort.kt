package com.bondary.application.member.out

import com.bondary.member.Member
import com.bondary.member.MemberToken

interface MemberTokenPort {
    fun generateRegisterToken(
        socialId: String,
        oAuthProvider: String,
        username: String,
        email: String,
        profileImage: String
    ): String

    fun generateAccessToken(member: Member?): String

    fun generateRefreshToken(member: Member?): String

    fun appendToken(token: MemberToken)
}
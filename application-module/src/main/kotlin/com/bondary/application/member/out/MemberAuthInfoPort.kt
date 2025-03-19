package com.bondary.application.member.out

import com.bondary.member.MemberAuth
import com.bondary.member.OAuthProvider

interface MemberAuthInfoPort {
    fun getMemberAuthInfo(
        socialId: String,
        oAuthProvider: OAuthProvider
    ): MemberAuth?
}
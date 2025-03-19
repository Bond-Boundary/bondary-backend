package com.bondary.application.member

import com.bondary.member.OAuthProvider

data class OAuthMemberInfo(
    val memberName: String,
    val memberEmail: String,
    val memberProfileImage: String,
    val socialId: String,
    val oAuthProvider: OAuthProvider,
)

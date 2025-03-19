package com.bondary.application.member

import com.bondary.member.OAuthProvider
import com.bondary.member.SocialId

data class OAuthMemberInfo(
    val memberName: String,
    val memberEmail: String,
    val memberProfileImage: String,
    val socialId: SocialId,
    val oAuthProvider: OAuthProvider,
)

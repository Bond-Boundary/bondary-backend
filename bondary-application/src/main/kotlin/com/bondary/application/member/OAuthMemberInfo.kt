package com.bondary.application.member

import com.bondary.OAuthProvider
import com.bondary.SocialId

data class OAuthMemberInfo(
    val name: String,
    val email: String,
    val profileImage: String,
    val socialId: SocialId,
    val oAuthProvider: OAuthProvider,
)

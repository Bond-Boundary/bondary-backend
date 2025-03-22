package com.bondary.security

import com.bondary.member.OAuthProvider
import com.bondary.member.SocialId

class MemberRegisterJwtClaims(
    val name: String,
    val email: String,
    val profileImage: String,
    val socialId: SocialId,
    val oAuthProvider: OAuthProvider,
) : JwtClaims
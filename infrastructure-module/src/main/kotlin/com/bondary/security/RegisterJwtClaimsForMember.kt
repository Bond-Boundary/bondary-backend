package com.bondary.security

import com.bondary.OAuthProvider
import com.bondary.SocialId

class RegisterJwtClaimsForMember(
    val name: String,
    val email: String,
    val profileImage: String,
    val socialId: SocialId,
    val oAuthProvider: OAuthProvider,
) : JwtClaims
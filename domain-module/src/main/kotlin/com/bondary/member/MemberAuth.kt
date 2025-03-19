package com.bondary.member

import com.bondary.support.DomainId

class MemberAuth(
    id: DomainId,
    val memberId: DomainId,
    var socialId: String,
    val oauthProvider: OAuthProvider
)

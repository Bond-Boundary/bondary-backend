package com.bondary.security

class MemberJwtClaims(
    val memberId: String,
    val memberTokenType: MemberTokenType
) : JwtClaims {
    fun equalsTokenType(memberTokenType: MemberTokenType): Boolean = this.memberTokenType == memberTokenType
}
package com.bondary.security

class JwtClaimsForMember(
    val memberId: String,
    val jwtTokenType: JwtTokenType
) : JwtClaims {
    fun equalsTokenType(jwtTokenType: JwtTokenType): Boolean = this.jwtTokenType == jwtTokenType
}
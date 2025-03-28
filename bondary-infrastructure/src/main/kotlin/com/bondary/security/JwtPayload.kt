package com.bondary.security

import java.util.*

data class JwtPayload<T : JwtClaims>(
    val issuedAt: Date = Date(),
    val issuer: String,
    val subject: String,
    val expireTime: Long,
    val claims: T
)

package com.bondary.security

import com.bondary.member.Member
import com.bondary.support.exception.CoreException
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import org.springframework.stereotype.Component

@Component
class JwtManager(
    private val jwtProvider: JwtProvider,
    private val jwtProperties: JwtProperties
) {
    fun <T : JwtClaims> getDefaultPayload(
        jwtClaims: T,
        expireTime: Long,
    ): JwtPayload<T> =
        JwtPayload(
            issuer = "bondary",
            subject = "bondary_OAuth",
            expireTime = expireTime,
            claims = jwtClaims,
        )

    fun generateToken(member: Member, JWTTokenType: JwtTokenType): String {
        val jwtClaims = JwtClaimsForMember(
            memberId = member.id.value,
            JWTTokenType = JWTTokenType
        )
        val payload = getDefaultPayload(jwtClaims, 1000 * 60 * 10 * 24)
        return jwtProvider.createToken(payload, jwtProperties.secretKey)
    }

    fun resolveAccessOrRefreshTokenByType(
        JWTTokenType: JwtTokenType,
        resolve: () -> JwtPayload<JwtClaimsForMember>
    ): JwtPayload<JwtClaimsForMember> {
        return resolveToken {
            val jwtPayload = resolve()
            if (jwtPayload.claims.equalsTokenType(JWTTokenType).not()) {
                throw CoreException.InvalidTokenException("요청 토큰 타입이 올바르지 않습니다.")
            }
            return@resolveToken jwtPayload
        }
    }

    fun <T : JwtClaims> resolveToken(resolve: () -> JwtPayload<T>): JwtPayload<T> {
        try {
            return resolve()
        } catch (e: Exception) {
            when (e) {
                is MalformedJwtException -> throw CoreException.InvalidTokenException()
                is ExpiredJwtException -> throw CoreException.ExpiredTokenException()
                else -> throw e
            }
        }
    }
}
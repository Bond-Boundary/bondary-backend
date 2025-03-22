package com.bondary.persistence.jpa.member.adapter

import com.bondary.application.member.TokenType
import com.bondary.application.member.out.MemberTokenPort
import com.bondary.member.Member
import com.bondary.member.MemberToken
import com.bondary.member.OAuthProvider
import com.bondary.member.SocialId
import com.bondary.persistence.jpa.member.mapper.MemberAuthMapper
import com.bondary.persistence.jpa.member.repository.MemberTokenJpaRepository
import com.bondary.security.*
import com.bondary.security.JwtProvider.resolveToken
import com.bondary.security.MemberTokenType.ACCESS_TOKEN
import com.bondary.security.MemberTokenType.REFRESH_TOKEN
import com.bondary.support.DomainId
import org.springframework.stereotype.Repository

@Repository
class MemberTokenJpaAdapter(
    private val jwtManager: JwtManager,
    private val jwtProperties: JwtProperties,
    private val memberTokenJpaRepository: MemberTokenJpaRepository
) : MemberTokenPort {
    override fun generateRegisterToken(
        name: String,
        email: String,
        profileImage: String,
        socialId: String,
        oAuthProvider: String
    ): String {
        val jwtClaims = MemberRegisterJwtClaims(
            name = name,
            email = email,
            profileImage = profileImage,
            socialId = SocialId(socialId),
            oAuthProvider = OAuthProvider.parse(oAuthProvider)
        )

        val payload = jwtManager.getDefaultPayload(jwtClaims, 1000 * 60 * 10)
        return JwtProvider.createToken(payload, jwtProperties.secretKey)
    }

    override fun generateAccessToken(member: Member): String {
        return jwtManager.generateToken(member, ACCESS_TOKEN)
    }

    override fun generateRefreshToken(member: Member): String {
        return jwtManager.generateToken(member, REFRESH_TOKEN)
    }

    override fun saveToken(memberToken: MemberToken) {
        val memberTokenEntity = MemberAuthMapper.toMemberTokenEntity(memberToken)
        memberTokenJpaRepository.save(memberTokenEntity)
    }

    override fun isExistTokenByToken(token: String): Boolean =
        memberTokenJpaRepository.existsByToken(token)

    override fun isExistTokenByTokenAndDomainId(token: String, memberId: DomainId): Boolean =
        memberTokenJpaRepository.existsByTokenAndMemberId(token, memberId.value)

    override fun deleteToken(refreshToken: String) {
        memberTokenJpaRepository.deleteByToken(refreshToken)
    }

    override fun resolveRegisterToken(token: String): TokenType.RegisterToken {
        val jwtPayload: JwtPayload<MemberRegisterJwtClaims> =
            jwtManager.resolveToken { resolveToken(token, jwtProperties.secretKey) }

        val jwtClaims = jwtPayload.claims

        return TokenType.RegisterToken(
            socialId = jwtClaims.socialId,
            oAuthProvider = jwtClaims.oAuthProvider,
            name = jwtClaims.name,
            profileImage = jwtClaims.profileImage,
            email = jwtClaims.email,
        )
    }

    override fun resolveAccessToken(token: String): TokenType.AccessToken {
        val jwtPayload: JwtPayload<MemberJwtClaims> =
            jwtManager.resolveAccessOrRefreshTokenByType(ACCESS_TOKEN) {
                resolveToken(token, jwtProperties.secretKey)
            }
        val jwtClaims = jwtPayload.claims
        return TokenType.AccessToken(memberId = jwtClaims.memberId)
    }

    override fun resolveRefreshToken(token: String): TokenType.RefreshToken {
        val jwtPayload: JwtPayload<MemberJwtClaims> =
            jwtManager.resolveAccessOrRefreshTokenByType(REFRESH_TOKEN) {
                resolveToken(token, jwtProperties.secretKey)
            }
        val jwtClaims = jwtPayload.claims
        return TokenType.RefreshToken(memberId = jwtClaims.memberId)
    }
}
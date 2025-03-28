package com.bondary.application.member

import com.bondary.OAuthProvider
import com.bondary.application.member.`in`.OAuthLoginUseCase
import com.bondary.application.member.out.*
import com.bondary.member.MemberAuth
import com.bondary.member.MemberToken
import com.bondary.support.exception.CoreException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OAuthLoginService(
    private val memberOAuthCodeExchangePort: MemberOAuthCodeExchangePort,
    private val memberOAuthInfoPort: MemberOAuthInfoPort,
    private val memberAuthInfoPort: MemberAuthInfoPort,
    private val memberFunctionPort: MemberFunctionPort,
    private val memberTokenPort: MemberTokenPort
) : OAuthLoginUseCase {
    @Transactional
    override suspend fun login(command: OAuthLoginUseCase.Command): OAuthLoginUseCase.Response {
        val oauthProvider = OAuthProvider.parse(command.provider)

        val accessToken = memberOAuthCodeExchangePort.exchangeAuthCodeForToken(
            provider = oauthProvider,
            authorizationCode = command.authorizationCode,
            redirectUri = command.redirectUri
        )

        val oauthInfo = memberOAuthInfoPort.getOAuthMemberInfo(
            oauthProvider = OAuthProvider.parse(command.provider),
            accessToken = accessToken
        )

        val memberAuthInfo =  memberAuthInfoPort.getMemberAuthInfo(
            socialId = oauthInfo.socialId,
            oauthProvider = oauthInfo.oAuthProvider
        )

        return when (memberAuthInfo) {
            null -> handleNotRegisterMember(oauthInfo)
            else -> handleExistingMember(memberAuthInfo)
        }
    }

    private suspend fun handleExistingMember(memberAuthInfo: MemberAuth): OAuthLoginUseCase.Response.Success {
        val member = memberFunctionPort.getMember(memberAuthInfo.memberId!!)
            ?: throw CoreException.NotFoundData("회원 정보를 찾을 수 없습니다 : ${memberAuthInfo.memberId}")

        val access = memberTokenPort.generateAccessToken(member)
        val refresh = memberTokenPort.generateRefreshToken(member)

        memberTokenPort.saveToken(memberToken = MemberToken.append(memberId = member.id, token = refresh))

        return OAuthLoginUseCase.Response.Success(access, refresh, member.isOnboarding())
    }

    private suspend fun handleNotRegisterMember(oauthInfo: OAuthMemberInfo): OAuthLoginUseCase.Response.NonRegistered {
        val register = memberTokenPort.generateRegisterToken(
            name = oauthInfo.name,
            email = oauthInfo.email,
            profileImage= oauthInfo.profileImage,
            oAuthProvider = oauthInfo.oAuthProvider.name,
            socialId = oauthInfo.socialId.value
        )
//        memberTokenPort.saveToken(memberToken = MemberToken.append(token = register))

        return OAuthLoginUseCase.Response.NonRegistered(register)
    }

}
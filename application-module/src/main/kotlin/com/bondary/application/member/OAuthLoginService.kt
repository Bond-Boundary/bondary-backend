package com.bondary.application.member

import com.bondary.application.member.`in`.OAuthLoginUseCase
import com.bondary.application.member.out.*
import com.bondary.member.MemberAuth
import com.bondary.member.MemberToken
import com.bondary.member.OAuthProvider
import com.bondary.support.CoreErrorType
import com.bondary.support.CoreException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OAuthLoginService(
    private val oAuthMemberInfoPort: MemberOAuthInfoPort,
    private val memberAuthInfoPort: MemberAuthInfoPort,
    private val memberFunctionPort: MemberFunctionPort,
    private val memberTokenPort: MemberTokenPort
) : OAuthLoginUseCase {
    @Transactional
    override fun login(command: OAuthLoginUseCase.Command): OAuthLoginUseCase.Response {
        val oauthInfo = oAuthMemberInfoPort.getOAuthInfo(
            oauthProvider = OAuthProvider.parse(command.provider),
            accessToken = command.accessToken
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

    private fun handleExistingMember(memberAuthInfo: MemberAuth): OAuthLoginUseCase.Response.Success {
        val member = memberFunctionPort.getMember(memberAuthInfo.memberId)
            ?: throw CoreException.NotFoundData("회원 정보를 찾을 수 없습니다 : ${memberAuthInfo.memberId}")

        val access = memberTokenPort.generateAccessToken(member)
        val refresh = memberTokenPort.generateRefreshToken(member)
        memberTokenPort.saveToken(MemberToken.append(memberId = member.id, token = refresh))

        return OAuthLoginUseCase.Response.Success(access, refresh, member.isOnboarding())
    }

    private fun handleNotRegisterMember(oauthInfo: OAuthMemberInfo): OAuthLoginUseCase.Response.NonRegistered {
        val register = memberTokenPort.generateRegisterToken(
            name = oauthInfo.memberName,
            email = oauthInfo.memberEmail,
            profileImage= oauthInfo.memberProfileImage,
            oAuthProvider = oauthInfo.oAuthProvider.name,
            socialId = oauthInfo.socialId
        )
        memberTokenPort.saveToken(MemberToken.append(token = register))

        return OAuthLoginUseCase.Response.NonRegistered(register)
    }

}
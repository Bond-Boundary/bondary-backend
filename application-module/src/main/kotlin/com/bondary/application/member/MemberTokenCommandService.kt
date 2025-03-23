package com.bondary.application.member

import com.bondary.application.member.`in`.TokenUseCase
import com.bondary.application.member.out.MemberAuthInfoPort
import com.bondary.application.member.out.MemberFunctionPort
import com.bondary.application.member.out.MemberTokenPort
import com.bondary.support.exception.CoreException
import com.bondary.support.DomainId
import org.springframework.stereotype.Service

@Service
class MemberTokenCommandService(
    private val memberAuthInfoPort: MemberAuthInfoPort,
    private val memberFunctionPort: MemberFunctionPort,
    private val memberTokenPort: MemberTokenPort
) : TokenUseCase {
    override fun resolveAccessToken(token: String): TokenUseCase.Response {
        val access = memberTokenPort.resolveAccessToken(token)
        return TokenUseCase.Response.Success(access.memberId)
    }

    override fun logout(command: TokenUseCase.Response.Command) {
        if(!memberTokenPort.isExistTokenByTokenAndDomainId(
            token = command.refreshToken,
            memberId = DomainId(command.memberId)
        )) {
            throw CoreException.NotFoundData("토큰이 없습니다.")
        }
        memberTokenPort.deleteToken(command.refreshToken)
    }
}
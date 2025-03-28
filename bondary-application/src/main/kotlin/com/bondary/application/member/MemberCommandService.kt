package com.bondary.application.member

import com.bondary.application.member.`in`.AppendMemberUseCase
import com.bondary.application.member.out.MemberAuthInfoPort
import com.bondary.application.member.out.MemberFunctionPort
import com.bondary.application.member.out.MemberTokenPort
import com.bondary.member.Member
import com.bondary.member.MemberAuth
import com.bondary.member.MemberToken
import com.bondary.support.exception.CoreException
import org.springframework.stereotype.Service

@Service
class MemberCommandService(
    private val memberAuthInfoPort: MemberAuthInfoPort,
    private val memberFunctionPort: MemberFunctionPort,
    private val memberTokenPort: MemberTokenPort
) : AppendMemberUseCase {
    override suspend fun appendMember(command: AppendMemberUseCase.Command): AppendMemberUseCase.Response.Success {
//        if (!memberTokenPort.isExistTokenByToken(command.token)) {
//            throw CoreException.NotFoundData()
//        }
        val resolved = memberTokenPort.resolveRegisterToken(command.token)

        if (memberAuthInfoPort.isExistsMemberAuthInfo(
                socialId = resolved.socialId,
                oAuthProvider = resolved.oAuthProvider
            )
        ) {
            throw CoreException.DataAlreadyExists("이미 가입된 멤버 입니다.")
        }
        val append = Member.append(
            name = resolved.name,
            profileImage = resolved.profileImage,
            introduction = command.introduction,
            schoolName = command.schoolName,
            firstMajorName = command.firstMajorName,
            secondaryMajorName = command.secondaryMajorName,
            interestArea = command.interestArea,
            interestJob = command.interestJob,
            instagram = command.instagram,
            linkedin = command.linkedin,
            etcLinks = command.etcLinks
        )

        val memberOAuth = MemberAuth.append(
            memberId = append.id,
            socialId = resolved.socialId,
            oAuthProvider = resolved.oAuthProvider
        )

        memberFunctionPort.save(append)
        memberAuthInfoPort.saveMemberAuthInfo(memberOAuth)

        val access = memberTokenPort.generateAccessToken(append)
        val refresh = memberTokenPort.generateRefreshToken(append)

        memberTokenPort.saveToken(MemberToken.append(memberId = append.id, token = refresh))
        return AppendMemberUseCase.Response.Success(access, refresh)
    }
}
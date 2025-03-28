package com.bondary.controller

import com.bondary.application.member.`in`.AppendMemberUseCase
import com.bondary.controller.v1.request.AppendMemberRequest
import com.bondary.controller.v1.response.AppendMemberResponse
import com.bondary.support.auth.RegisterToken
import com.bondary.support.exception.ApiResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberController(
    private val appendMemberUseCase: AppendMemberUseCase
) {
    @PostMapping("/v1/members")
    suspend fun appendMember(
        @RegisterToken registerToken: String,
        @RequestBody request: AppendMemberRequest
    ): ApiResponse<AppendMemberResponse> {
        val response = appendMemberUseCase.appendMember(
            AppendMemberUseCase.Command(
                token = registerToken,
                introduction = request.introduction,
                schoolName = request.schoolName,
                firstMajorName = request.firstMajorName,
                secondaryMajorName = request.secondaryMajorName,
                interestArea = request.interestArea,
                interestJob = request.interestJob,
                instagram = request.instagram,
                linkedin = request.linkedin,
                etcLinks = request.etcLinks
            )
        )
        return ApiResponse.success(AppendMemberResponse.of(response))
    }
}
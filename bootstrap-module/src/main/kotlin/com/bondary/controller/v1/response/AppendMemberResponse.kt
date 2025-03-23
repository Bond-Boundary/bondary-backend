package com.bondary.controller.v1.response

import com.bondary.application.member.`in`.AppendMemberUseCase

data class AppendMemberResponse(
    val accessToken: String,
    val refreshToken: String
) {
    companion object {
        fun of(response: AppendMemberUseCase.Response.Success): AppendMemberResponse =
            AppendMemberResponse(
                accessToken = response.accessToken,
                refreshToken = response.refreshToken
            )
    }
}
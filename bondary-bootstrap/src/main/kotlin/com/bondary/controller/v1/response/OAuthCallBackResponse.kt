package com.bondary.controller.v1.response

data class OAuthCallBackResponse(
    val message: String = "인증 코드가 성공적으로 수신되었습니다.",
    val code: String
) {
    companion object {
        fun of(code: String): OAuthCallBackResponse =
            OAuthCallBackResponse(code = code)
    }
}
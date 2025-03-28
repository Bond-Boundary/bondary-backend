package com.bondary.member.exception

import com.bondary.support.exception.CoreErrorType
import com.bondary.support.exception.CoreException

sealed class MemberException(
    errorType: CoreErrorType,
    data: String? = null
) : CoreException(errorType, data) {
    class InvalidTokenException : MemberException(CoreErrorType.INVALID_TOKEN, "유효하지 않은 토큰입니다.")

    class InvalidTokenTypeException : MemberException(CoreErrorType.INVALID_TOKEN, "요청 토큰 타입이 올바르지 않습니다.")

    class ExpiredTokenException : MemberException(CoreErrorType.EXPIRED_TOKEN, "만료된 토큰입니다.")
}

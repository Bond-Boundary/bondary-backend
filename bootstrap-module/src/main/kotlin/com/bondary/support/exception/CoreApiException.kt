package com.bondary.support.exception

sealed class CoreApiException(
    open val errorType: ApiErrorType,
    open val data: String? = null
) : RuntimeException(data) {
    /**
     * 추가 API 예외 타입을 여기에 추가
     */
    class ServerError(
        data: String? = null
    ) : CoreApiException(ApiErrorType.SERVER_ERROR, data)

    class BadRequest(
        data: String? = null
    ) : CoreApiException(ApiErrorType.BAD_REQUEST, data)

    /**
     * 기본 예외 클래스 - 커스텀 오류 타입을 직접 지정할 때 사용
     */
    class Default(
        override val errorType: ApiErrorType,
        override val data: String? = null
    ) : CoreApiException(errorType, data)
}
package io.dodn.springboot.core.support.error

sealed class CoreApiException(
    open val errorType: ApiErrorType,
    open val data: Any? = null,
    message: String = errorType.message
) : RuntimeException(message) {
    /**
     * 추가 API 예외 타입을 여기에 추가
     */
    class ServerError(
        data: Any? = null
    ) : CoreApiException(ApiErrorType.SERVER_ERROR, data)

    /**
     * 기본 예외 클래스 - 커스텀 오류 타입을 직접 지정할 때 사용
     */
    class Default(
        override val errorType: ApiErrorType,
        override val data: Any? = null
    ) : CoreApiException(errorType, data)
}
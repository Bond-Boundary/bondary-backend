package io.dodn.springboot.core.support.response

import io.dodn.springboot.core.support.error.ApiErrorMessage
import io.dodn.springboot.core.support.error.ApiErrorType

data class ApiResponse<T> private constructor(
    val result: ApiResultType,
    val data: T? = null,
    val error: ApiErrorMessage? = null,
) {
    companion object {
        fun success(): ApiResponse<Any> {
            return ApiResponse(ApiResultType.SUCCESS, null, null)
        }

        fun <S> success(data: S): ApiResponse<S> {
            return ApiResponse(ApiResultType.SUCCESS, data, null)
        }

        fun <S> error(error: ApiErrorType, errorData: Any? = null): ApiResponse<S> {
            return ApiResponse(ApiResultType.ERROR, null, ApiErrorMessage(error, errorData))
        }
    }
}

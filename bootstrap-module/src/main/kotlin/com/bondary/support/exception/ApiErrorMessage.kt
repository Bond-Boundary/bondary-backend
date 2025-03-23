package com.bondary.support.exception

data class ApiErrorMessage(
    val code: String,
    val message: String,
    val data: Any? = null
) {
    constructor(apiErrorType: ApiErrorType, data: Any? = null) : this(
        code = apiErrorType.code.name,
        message = apiErrorType.message,
        data = data,
    )

    constructor(exception: CoreApiException) : this(
        code = exception.errorType.code.name,
        message = exception.errorType.message,
        data = exception.data,
    )
}


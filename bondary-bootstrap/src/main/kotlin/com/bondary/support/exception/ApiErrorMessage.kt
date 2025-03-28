package com.bondary.support.exception

data class ApiErrorMessage(
    val code: String,
    val message: Any? = null,
) {
    constructor(apiErrorType: ApiErrorType, errorData: Any?) : this(
        code = apiErrorType.code.name,
        message = errorData,
    )

    constructor(error: CoreErrorType, errorData: Any?) : this(
        code = error.code.name,
        message = errorData,
    )
}


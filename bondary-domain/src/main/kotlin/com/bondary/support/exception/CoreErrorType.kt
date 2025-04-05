package com.bondary.support.exception;

enum class CoreErrorType(val kind: CoreErrorKind, val code: CoreErrorCode, val level: CoreErrorLevel) {
    NOT_FOUND_DATA(CoreErrorKind.SERVER_ERROR, CoreErrorCode.ERROR_1000, CoreErrorLevel.INFO),
    DATA_IS_ALREADY_EXIST(CoreErrorKind.SERVER_ERROR, CoreErrorCode.ERROR_1001, CoreErrorLevel.INFO),

    EXPIRED_TOKEN(CoreErrorKind.SERVER_ERROR, CoreErrorCode.ERROR_1002, CoreErrorLevel.INFO),

    INVALID_ARGUMENT(CoreErrorKind.SERVER_ERROR, CoreErrorCode.ERROR_1003, CoreErrorLevel.INFO),
    INVALID_TOKEN(CoreErrorKind.SERVER_ERROR, CoreErrorCode.ERROR_1003, CoreErrorLevel.INFO),

    VALUE_IS_EMPTY(CoreErrorKind.SERVER_ERROR, CoreErrorCode.ERROR_1004, CoreErrorLevel.INFO),
    VALUE_IS_OVER_LENGTH(CoreErrorKind.SERVER_ERROR, CoreErrorCode.ERROR_1004, CoreErrorLevel.INFO)
}

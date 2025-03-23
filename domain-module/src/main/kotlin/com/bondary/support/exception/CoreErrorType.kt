package com.bondary.support.exception;

enum class CoreErrorType(
    val kind: CoreErrorKind,
    val code: CoreErrorCode,
    val message: String,
    val level: CoreErrorLevel
) {
    NOT_FOUND_DATA(
        CoreErrorKind.SERVER_ERROR,
        CoreErrorCode.ERROR_1000,
        "해당 데이터를 찾을 수 없습니다.",
        CoreErrorLevel.INFO
            ),

    DATA_IS_ALREADY_EXIST(
        CoreErrorKind.SERVER_ERROR,
        CoreErrorCode.ERROR_1001,
        "해당 데이터는 이미 존재합니다.",
        CoreErrorLevel.INFO
            ),

    EXPIRED_TOKEN(
        CoreErrorKind.SERVER_ERROR,
        CoreErrorCode.ERROR_1002,
        "만료된 토큰입니다..",
        CoreErrorLevel.INFO
    ),

    INVALID_ARGUMENT(
        CoreErrorKind.SERVER_ERROR,
        CoreErrorCode.ERROR_1003,
        "유효하지 않은 인자입니다.",
        CoreErrorLevel.INFO
    ),

    INVALID_TOKEN(
        CoreErrorKind.SERVER_ERROR,
        CoreErrorCode.ERROR_1003,
        "유효하지 않은 토큰입니다.",
        CoreErrorLevel.INFO
    ),


}

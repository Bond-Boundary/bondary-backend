package com.bondary.support;

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
}

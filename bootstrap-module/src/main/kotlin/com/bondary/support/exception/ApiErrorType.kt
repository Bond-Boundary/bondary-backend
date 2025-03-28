package com.bondary.support.exception

import org.springframework.boot.logging.LogLevel
import org.springframework.http.HttpStatus

enum class ApiErrorType(val status: HttpStatus, val code: ApiErrorCode, val logLevel: LogLevel) {
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, ApiErrorCode.ERROR_500, LogLevel.ERROR),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, ApiErrorCode.ERROR_400, LogLevel.ERROR)
}

package com.bondary.support.exception

import org.springframework.boot.logging.LogLevel
import org.springframework.http.HttpStatus

enum class ApiErrorType(val status: HttpStatus, val code: ApiErrorCode, val message: String, val logLevel: LogLevel) {
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, ApiErrorCode.ERROR_500, "예상치 못한 서버 에러가 발생하였습니다.", LogLevel.ERROR),
}

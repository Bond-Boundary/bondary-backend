package io.dodn.springboot.core.support.error

import org.springframework.boot.logging.LogLevel
import org.springframework.http.HttpStatus

enum class ErrorType(val status: HttpStatus, val code: ErrorCode, val message: String, val logLevel: LogLevel) {
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.ERROR_500, "예상치 못한 서버 에러가 발생하였습니다.", LogLevel.ERROR),
}

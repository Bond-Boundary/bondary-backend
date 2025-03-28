package com.bondary.support

import com.bondary.support.exception.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.logging.LogLevel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiControllerAdvice {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(CoreApiException::class)
    fun handleCoreApiException(e: CoreApiException): ResponseEntity<ApiResponse<Any>> {
        when (e.errorType.logLevel) {
            LogLevel.ERROR -> log.error("CoreApiException : {}", e.message, e)
            LogLevel.WARN -> log.warn("CoreApiException : {}", e.message, e)
            else -> log.info("CoreApiException : {}", e.message, e)
        }
        return ResponseEntity(
            ApiResponse.error(e.errorType, e.data),
            e.errorType.status
        )
    }

    @ExceptionHandler(CoreException::class)
    fun handleCoreException(e: CoreException): ResponseEntity<ApiResponse<Any>> {
        when (e.errorType.level) {
            CoreErrorLevel.ERROR -> log.error("CoreApiException: {}", e.errorType, e)
            CoreErrorLevel.WARNING -> log.warn("CoreApiException: {}", e.errorType, e)
            else -> log.info("CoreException: {}", e.errorType, e)
        }

        val status = when (e.errorType.kind) {
            CoreErrorKind.CLIENT_ERROR -> HttpStatus.BAD_REQUEST
            CoreErrorKind.SERVER_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR
        }

        val errorData = mapOf(
            "error_code" to e.errorType.code.name,
            "error_message" to e.data,
            "error_level" to e.errorType.level.name,
        )

        return ResponseEntity(
            ApiResponse.error(e.errorType, errorData),
            status
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ApiResponse<Any>> {
        log.error("Exception : {}", e.message, e)
        return ResponseEntity(
            ApiResponse.error(ApiErrorType.SERVER_ERROR),
            ApiErrorType.SERVER_ERROR.status
        )
    }
}
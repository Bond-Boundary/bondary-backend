package com.bondary.support

import io.dodn.springboot.core.support.error.ApiErrorType
import io.dodn.springboot.core.support.error.CoreApiException
import io.dodn.springboot.core.support.response.ApiResponse
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
            CoreErrorLevel.ERROR -> log.error("CoreApiException: {}", e.errorType.message, e)
            CoreErrorLevel.WARNING -> log.warn("CoreApiException: {}", e.errorType.message, e)
            else -> log.info("CoreException: {}", e.errorType.message, e)
        }

        val status = when (e.errorType.kind) {
            CoreErrorKind.CLIENT_ERROR -> HttpStatus.BAD_REQUEST
            CoreErrorKind.SERVER_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR
        }

        val apiErrorType = ApiErrorType.SERVER_ERROR

        val errorData = mapOf(
            "error_code" to e.errorType.code.name,
            "error_message" to e.errorType.message,
            "error_level" to e.errorType.level.name,
            "data" to e.data
        )

        return ResponseEntity(
            ApiResponse.error(apiErrorType, errorData),
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
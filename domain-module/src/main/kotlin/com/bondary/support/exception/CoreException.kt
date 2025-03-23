package com.bondary.support.exception

sealed class CoreException(
    open val errorType: CoreErrorType,
    open val data: Any? = null,
    message: String = errorType.message
) : RuntimeException(message) {

    class NotFoundData(
        data: Any? = null
    ) : CoreException(CoreErrorType.NOT_FOUND_DATA, data)

    class DataAlreadyExists(
        data: Any? = null
    ) : CoreException(CoreErrorType.DATA_IS_ALREADY_EXIST, data)

    class InvalidArgument(
        data: Any? = null
    ) :  CoreException(CoreErrorType.INVALID_ARGUMENT, data)

    class InvalidTokenException(
        data: Any? = null
    ) :  CoreException(CoreErrorType.INVALID_TOKEN, data)

    class ExpiredTokenException(
        data: Any? = null
    ) :  CoreException(CoreErrorType.EXPIRED_TOKEN, data)

    class Default(
        override val errorType: CoreErrorType,
        override val data: Any? = null
    ) : CoreException(errorType, data)
}
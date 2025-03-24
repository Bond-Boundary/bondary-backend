package com.bondary.support.exception

abstract class CoreException(
    open val errorType: CoreErrorType,
    open val data: String? = null,
) : RuntimeException(data) {

    class NotFoundData(
        data: String? = null
    ) : CoreException(CoreErrorType.NOT_FOUND_DATA, data)

    class DataAlreadyExists(
        data: String? = null
    ) : CoreException(CoreErrorType.DATA_IS_ALREADY_EXIST, data)

    class InvalidArgument(
        data: String? = null
    ) : CoreException(CoreErrorType.INVALID_ARGUMENT, data)

    class ValueIsEmptyException(
        data: String? = null
    ) : CoreException(CoreErrorType.VALUE_IS_EMPTY, data)

    class ValueLengthException(
        data: String? = null
    ) : CoreException(CoreErrorType.VALUE_IS_OVER_LENGTH, data)

    class Default(
        override val errorType: CoreErrorType,
        override val data: String? = null
    ) : CoreException(errorType, data)
}
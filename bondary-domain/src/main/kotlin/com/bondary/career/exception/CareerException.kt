package com.bondary.career.exception

import com.bondary.support.exception.CoreErrorType
import com.bondary.support.exception.CoreException

sealed class CareerException(
    errorType: CoreErrorType,
    data: String? = null
) : CoreException(errorType, data) {
    class TitleEmpty : CareerException(CoreErrorType.VALUE_IS_EMPTY, "경력 제목은 필수 입력 사항입니다.")

    class ContentEmpty : CareerException(CoreErrorType.VALUE_IS_EMPTY, "경력 내용은 필수 입력 사항입니다.")

    class ContentTooLong(maxLength: Int = 500) : CareerException(CoreErrorType.VALUE_IS_OVER_LENGTH, "경력 내용은 최대 ${maxLength}자까지 입력 가능합니다.")

    class EndDateRequired : CareerException(CoreErrorType.VALUE_IS_EMPTY, "진행 중이 아닌 경력은 종료일이 필요합니다.")

    class EndDateNotRequired : CareerException(CoreErrorType.INVALID_ARGUMENT, "진행 중인 경력은 종료일이 필요하지 않습니다..")

    class InvalidDateRange : CareerException(CoreErrorType.INVALID_ARGUMENT, "종료일은 시작일보다 이후여야 합니다.")

    class CareerNotFound : CareerException(CoreErrorType.NOT_FOUND_DATA, "경력을 찾지 못하였습니다.")
}

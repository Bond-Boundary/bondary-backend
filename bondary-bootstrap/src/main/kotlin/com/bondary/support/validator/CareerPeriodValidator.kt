package com.bondary.support.validator

import com.bondary.controller.v1.request.CreateCareerRequest
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class CareerPeriodValidator : ConstraintValidator<ValidCareerPeriod, CreateCareerRequest> {
    override fun isValid(
        request: CreateCareerRequest,
        context: ConstraintValidatorContext
    ): Boolean {
        context.disableDefaultConstraintViolation()

        /**
         * 진행 중인 경력은 종료일이 없어야 함
         */
        if (request.isProgress && request.careerEnd != null) {
            context.buildConstraintViolationWithTemplate("진행 중인 경력은 종료 일을 입력할 수 없습니다.")
                .addConstraintViolation()
            return false
        }

        /**
         * 완료된 경력은 종료일이 있어야 함
         */
        if (!request.isProgress && request.careerEnd == null) {
            context.buildConstraintViolationWithTemplate("진행 중이지 않은 경력은 종료 일을 입력해야 합니다.")
                .addConstraintViolation()
            return false
        }

        return true
    }
}
package com.bondary.support

import java.time.LocalDateTime

abstract class BaseDomain(
    val id: DomainId = DomainId.generate(),
    val createdAt: LocalDateTime,
    var updatedAt: LocalDateTime
) {
    protected fun updateTime() {
        updatedAt = LocalDateTime.now()
    }
}
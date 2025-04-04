package com.bondary.support

import java.time.LocalDateTime

abstract class BaseDomain(
    val id: DomainId = DomainId.generate(),
    val createdAt: LocalDateTime,
    var updatedAt: LocalDateTime
) {
    var domainStatus: DomainStatus = DomainStatus.ACTIVE

    protected fun updateTime() {
        updatedAt = LocalDateTime.now()
    }

    fun active() {
        domainStatus = DomainStatus.ACTIVE
    }

    fun delete() {
        domainStatus = DomainStatus.DELETE
    }
}
package com.bondary.support

import java.time.LocalDateTime

abstract class BaseDomain(
    val id: DomainId = DomainId.generate(),
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
}
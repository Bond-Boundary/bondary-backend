package com.bondary.support

import java.time.LocalDateTime

abstract class AggregateDomain<T : AggregateDomain<T>>(
    id: DomainId,
    createdAt: LocalDateTime,
    updatedAt: LocalDateTime
) : BaseDomain(id, createdAt, updatedAt) {
    private val events: MutableList<DomainEvent<T>> = mutableListOf()

    fun appendEvent(event: DomainEvent<T>) {
        events.add(event)
    }

    fun readEvent(): List<DomainEvent<T>> {
        val read = events.toList()
        events.clear()
        return read
    }
}


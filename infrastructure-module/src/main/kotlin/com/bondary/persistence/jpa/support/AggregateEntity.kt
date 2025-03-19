package com.bondary.persistence.jpa.support

abstract class AggregateEntity<T : AggregateEntity<T>>(
    override val id: String
) : BaseEntity(id)
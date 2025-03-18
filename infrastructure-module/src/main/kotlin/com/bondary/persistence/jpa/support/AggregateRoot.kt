package com.bondary.persistence.jpa.support

abstract class AggregateRoot<T : AggregateRoot<T>>(
    override val id: String
) : BaseEntity(id)
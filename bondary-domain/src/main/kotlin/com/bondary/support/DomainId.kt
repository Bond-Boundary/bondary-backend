package com.bondary.support

import com.fasterxml.uuid.Generators

@JvmInline
value class DomainId(
    val value: String
) {
    companion object{
        fun generate(): DomainId {
            return DomainId(Generators.timeBasedEpochGenerator().generate().toString())
        }
    }
}
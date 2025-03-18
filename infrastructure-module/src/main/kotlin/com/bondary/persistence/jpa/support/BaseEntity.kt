package com.bondary.persistence.jpa.support

import com.bondary.support.DomainId
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseEntity(
    @Id open val id: String = DomainId.generate().value
) {
    @CreationTimestamp
    var createdAt: LocalDateTime = LocalDateTime.MIN

    @UpdateTimestamp
    var updatedAt: LocalDateTime = LocalDateTime.MIN

    @Enumerated(EnumType.STRING)
    var entityStatus: EntityStatus = EntityStatus.ACTIVE

    fun active() {
        entityStatus = EntityStatus.ACTIVE
    }

    fun delete() {
        entityStatus = EntityStatus.DELETED
    }
}
package com.bondary.persistence.jpa

import com.bondary.persistence.jpa.support.AggregateRoot
import jakarta.persistence.*

@Entity
@Table(name = "relationship")
class RelationShipEntity(
    id: String,
    var sourceId: String,
    var targetId: String,

    @Enumerated(EnumType.STRING)
    @Column(
        name = "relation_type",
        nullable = false
    )
    var relationType: RelationType
) : AggregateRoot<RelationShipEntity>(id) {
}
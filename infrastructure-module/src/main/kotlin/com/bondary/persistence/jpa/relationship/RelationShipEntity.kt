package com.bondary.persistence.jpa.relationship

import com.bondary.persistence.jpa.member.entity.MemberEntity
import com.bondary.persistence.jpa.support.AggregateRoot
import jakarta.persistence.*

@Entity
@Table(name = "relationship")
class RelationShipEntity(
    id: String,

    @Column(name = "source_id", nullable = false)
    var sourceId: String,

    @Column(name = "target_id", nullable = false)
    var targetId: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "relation_type", nullable = false)
    var relationType: RelationType
) : AggregateRoot<RelationShipEntity>(id) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "source_id",
        nullable = false,
        insertable = false,
        updatable = false,
    )
    lateinit var source: MemberEntity

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "target_id",
        nullable = false,
        insertable = false,
        updatable = false,
    )
    lateinit var target: MemberEntity
}
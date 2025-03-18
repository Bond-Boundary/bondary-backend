package com.bondary.persistence.jpa.recommend_document

import com.bondary.persistence.jpa.member.entity.MemberEntity
import com.bondary.persistence.jpa.support.AggregateRoot
import jakarta.persistence.*

@Entity
@Table(name = "recommend_document")
class RecommendDocumentEntity(
    id: String,

    @Column(name = "source_id", nullable = false)
    var sourceId: String,

    @Column(name = "target_id", nullable = false)
    var targetId: String,

    @Column(name = "recommend_reason", length = 500)
    val recommendReason: String,
) : AggregateRoot<RecommendDocumentEntity>(id) {
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
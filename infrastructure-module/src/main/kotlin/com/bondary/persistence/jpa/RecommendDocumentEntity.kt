package com.bondary.persistence.jpa

import com.bondary.persistence.jpa.support.AggregateRoot
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "recommend_document")
class RecommendDocumentEntity(
    id: String,

    @Column(name = "source_id")
    var sourceId: String,

    @Column(name = "target_id")
    var targetId: String,

    @Column(length = 500)
    val recommendReason: String,
) : AggregateRoot<RecommendDocumentEntity>(id) {
}
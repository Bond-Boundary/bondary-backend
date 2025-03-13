package com.bondary.persistence.jpa

import com.bondary.persistence.jpa.support.AggregateRoot
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "member_career")
class MemberCareerEntity(
    id: String,
    val memberId: String,
    var thumbnailImage: String,
    var careerStart: LocalDateTime,
    var careerEnd: LocalDateTime,
    var isProgress: Boolean,
    var isRepresent: Boolean,
    var title: String,

    @Column(length = 500)
    var content: String,
) : AggregateRoot<MemberCareerEntity>(id) {
}
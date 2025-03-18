package com.bondary.persistence.jpa.member.entity

import com.bondary.persistence.jpa.support.BaseEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "member_career")
class MemberCareerEntity(
    id: String,

    @Column(name = "member_id", nullable = false)
    val memberId: String,

    @Column(name = "thumbnail_image", nullable = false)
    var thumbnailImage: String,

    @Column(name = "career_start", nullable = false)
    var careerStart: LocalDateTime,

    @Column(name = "career_end")
    var careerEnd: LocalDateTime?,

    @Column(name = "is_progress", nullable = false)
    var isProgress: Boolean,

    @Column(name = "is_represent", nullable = false)
    var isRepresent: Boolean,

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "content", length = 500, nullable = false)
    var content: String
) : BaseEntity(id) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "member_id",
        nullable = false,
        insertable = false,
        updatable = false,
    )
    lateinit var member: MemberEntity
}
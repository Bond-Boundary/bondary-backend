    package com.bondary.persistence.jpa.career.entity

    import com.bondary.career.Career
    import com.bondary.persistence.jpa.member.entity.MemberEntity
    import com.bondary.persistence.jpa.support.AggregateEntity
    import jakarta.persistence.*
    import java.time.LocalDateTime

    @Entity
    @Table(name = "career")
    class CareerEntity(
        id: String,

        @Column(name = "member_id", nullable = false)
        val memberId: String,

        @Column(name = "thumbnail_image", nullable = false)
        var thumbnailImage: String,

        @Column(name = "title", nullable = false)
        var title: String,

        @Column(name = "content", length = 500, nullable = false)
        var content: String,

        @Column(name = "career_start", nullable = false)
        var careerStart: LocalDateTime,

        @Column(name = "career_end")
        var careerEnd: LocalDateTime?,

        @Column(name = "is_progress", nullable = false)
        var isProgress: Boolean,

        @Column(name = "is_represent", nullable = false)
        var isRepresent: Boolean
    ) : AggregateEntity<CareerEntity>(id) {
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(
            name = "member_id",
            nullable = false,
            insertable = false,
            updatable = false,
        )
        lateinit var member: MemberEntity

        fun modify(career: Career) {
            this.thumbnailImage = career.careerDetails.thumbnailImage
            this.title = career.careerDetails.title
            this.content = career.careerDetails.content
            this.careerStart = career.careerPeriod.careerStart
            this.careerEnd = career.careerPeriod.careerEnd
            this.isProgress = career.careerPeriod.isProgress
            this.isRepresent = career.isRepresent
        }
    }
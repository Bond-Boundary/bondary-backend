package com.bondary.persistence.jpa.member.entity

import com.bondary.persistence.jpa.support.AggregateEntity
import com.bondary.persistence.jpa.support.StringListConverter
import jakarta.persistence.*

@Entity
@Table(name = "members")
class MemberEntity(
    id: String,

    @Column(name = "introduction", nullable = false)
    var introduction: String,

    @Column(name = "school_name", nullable = false)
    var schoolName: String,

    @Column(name = "first_major_name", nullable = false)
    var firstMajorName: String,

    @Column(name = "secondary_major_name")
    var secondaryMajorName: String?,

    @Column(name = "instagram")
    var instagram: String?,

    @Column(name = "linkedin")
    var linkedin: String?,

    @Enumerated(EnumType.STRING)
    @Column(name = "interest_area", nullable = false)
    var interestArea: InterestArea,

    @Convert(converter = StringListConverter::class)
    @Column(name = "etc_links", columnDefinition = "text")
    var etcLinks: List<String>?,
) : AggregateEntity<MemberEntity>(id) {
}
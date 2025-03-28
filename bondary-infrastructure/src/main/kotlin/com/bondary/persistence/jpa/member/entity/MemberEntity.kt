package com.bondary.persistence.jpa.member.entity

import com.bondary.persistence.jpa.support.AggregateEntity
import com.bondary.persistence.jpa.support.InterestAreaListConverter
import com.bondary.persistence.jpa.support.StringListConverter
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "members")
class MemberEntity(
    id: String,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "profile_image", nullable = false)
    var profileImage: String,

    @Column(name = "introduction", nullable = false)
    var introduction: String,

    @Column(name = "school_name", nullable = false)
    var schoolName: String,

    @Column(name = "first_major_name", nullable = false)
    var firstMajorName: String,

    @Column(name = "secondary_major_name")
    var secondaryMajorName: String?,

    @Convert(converter = InterestAreaListConverter::class)
    @Column(name = "interest_areas", columnDefinition = "text")
    var interestArea: List<InterestArea>,

    @Column(name = "interest_job")
    var interestJob: String?,

    @Column(name = "instagram")
    var instagram: String?,

    @Column(name = "linkedin")
    var linkedin: String?,

    @Convert(converter = StringListConverter::class)
    @Column(name = "etc_links", columnDefinition = "text")
    var etcLinks: List<String>?,

    @Column(name = "onboarding_at")
    val onBoardingAt: LocalDateTime?
) : AggregateEntity<MemberEntity>(id) {
}
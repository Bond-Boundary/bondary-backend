package com.bondary.persistence.jpa

import com.bondary.persistence.jpa.support.AggregateRoot
import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "members")
class MemberEntity(
    id: String,
    var introduction: String,
    var schoolName: String,
    var firstMajorName: String,
    var secondaryMajorName: String,
    var instagram: String?,
    var linkedIn: String?,

    @Enumerated(EnumType.STRING)
    @Column(
        name = "interest_area",
        nullable = false
    )
    var interestArea: InterestArea,

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(
        name = "images",
        columnDefinition = "text",
    )
    var etcLinks: List<String>?,
) : AggregateRoot<MemberEntity>(id) {
}
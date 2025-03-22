package com.bondary.persistence.jpa.member.entity

import com.bondary.persistence.jpa.support.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "member_token")
class MemberTokenEntity(
    id: String,

    @Column(name = "member_id")
    val memberId: String? = null,

    @Column(name = "token", length = 400)
    val token: String,
) : BaseEntity(id) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "member_id",
        insertable = false,
        updatable = false,
    )
    var member: MemberEntity? = null
}
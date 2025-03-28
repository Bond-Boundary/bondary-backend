package com.bondary.persistence.jpa.member.entity

import com.bondary.OAuthProvider
import com.bondary.persistence.jpa.support.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "member_auth")
class MemberAuthEntity(
    id: String,

    @Column(name = "member_id", nullable = false)
    val memberId: String,

    @Column(name = "social_id")
    val socialId: String,

    @Column(name = "oauth_provider", nullable = false)
    @Enumerated(EnumType.STRING)
    val oAuthProvider: OAuthProvider
) : BaseEntity(id) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "member_id",
        insertable = false,
        updatable = false,
    )
    var member: MemberEntity? = null
}
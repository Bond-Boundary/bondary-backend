package com.bondary.persistence.jpa.member.repository

import com.bondary.persistence.jpa.member.entity.MemberEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MemberJpaRepository : JpaRepository<MemberEntity, String>
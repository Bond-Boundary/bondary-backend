package com.bondary.persistence.jpa.member.repository

import com.bondary.persistence.jpa.member.entity.MemberCareerEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MemberCareerJpaRepository : JpaRepository<MemberCareerEntity, String>
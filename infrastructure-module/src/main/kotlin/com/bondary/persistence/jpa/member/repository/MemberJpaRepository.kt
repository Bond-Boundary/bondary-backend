package com.bondary.persistence.jpa.member.repository

import com.bondary.persistence.jpa.member.entity.MemberEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface MemberJpaRepository : JpaRepository<MemberEntity, String> {

    /**
     * 이름을 포함하는 회원 검색 (대소문자 구분 없음)
     */
    fun findByNameContainingIgnoreCase(name: String, pageable: Pageable): List<MemberEntity>

    /**
     * 이름을 포함하는 회원 검색 (대소문자 구분 없음, JPQL 쿼리 사용)
     */
    @Query("SELECT m FROM MemberEntity m WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :name, '%')) ORDER BY m.name")
    fun findByNameContainingWithLimit(
        @Param("name") name: String,
        pageable: Pageable
    ): List<MemberEntity>
}
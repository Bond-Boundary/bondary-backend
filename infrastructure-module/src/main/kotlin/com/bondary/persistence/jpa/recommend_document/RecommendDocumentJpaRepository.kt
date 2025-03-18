package com.bondary.persistence.jpa.recommend_document

import org.springframework.data.jpa.repository.JpaRepository

interface RecommendDocumentJpaRepository : JpaRepository<RecommendDocumentEntity, String>
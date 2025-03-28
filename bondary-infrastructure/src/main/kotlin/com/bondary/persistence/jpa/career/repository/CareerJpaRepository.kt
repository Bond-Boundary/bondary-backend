package com.bondary.persistence.jpa.career.repository

import com.bondary.persistence.jpa.career.entity.CareerEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CareerJpaRepository : JpaRepository<CareerEntity, String>
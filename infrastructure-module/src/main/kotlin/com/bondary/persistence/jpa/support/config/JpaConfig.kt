package com.bondary.persistence.jpa.support.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = ["com.bondary.persistence.jpa"])
@EntityScan(basePackages = ["com.bondary.persistence.jpa"])
class JpaConfig
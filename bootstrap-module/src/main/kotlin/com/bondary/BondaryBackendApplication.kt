package com.bondary;

import com.bondary.persistence.jpa.support.config.JpaConfig
import com.bondary.security.JwtConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(
	value = [
		JpaConfig::class,
		JwtConfig::class,
		InfraConfig::class
	],
)
class BondaryBackendApplication{

}

fun main(args: Array<String>) {
	runApplication<BondaryBackendApplication>(*args)
}

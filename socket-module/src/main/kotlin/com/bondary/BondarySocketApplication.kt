package com.bondary

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.bondary"])
class BondarySocketApplication

fun main(args: Array<String>) {
    runApplication<BondarySocketApplication>(*args)
}

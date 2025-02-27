package com.bondary

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BondaryRealtimeApplication

fun main(args: Array<String>) {
    runApplication<BondaryRealtimeApplication>(*args)
}

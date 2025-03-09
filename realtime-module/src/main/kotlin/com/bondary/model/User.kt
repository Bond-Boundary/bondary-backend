package com.bondary.model

import org.springframework.data.annotation.Id

data class User (
    @Id
    val id: String,
    val name: String,
    val thumbnailUrl: String,
    val description: String
)

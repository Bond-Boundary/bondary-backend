package com.bondary.support

data class PagedResult<T>(
    val content: List<T>,
    val totalElements: Long,
    val totalPages: Long
)

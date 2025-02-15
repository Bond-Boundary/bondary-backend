package com.bondary.support

data class Cursor(
    val offset: Long,
    val limit: Long,
    val sortType: SortType
)

package com.bondary.support

data class SliceResult<T>(
    val content: List<T>,
    val hasNext: Boolean,
    val nextKey: String? = null
)

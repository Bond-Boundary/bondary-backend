package com.bondary.support

data class SliceResponse<T>(
    val content: T,
    val offset: Long,
    val limit: Long,
    val totalItems: Long,
    val totalPages: Long
) {
    companion object {
        fun <T, U> of(
            content: T,
            offset: Long,
            limit: Long,
            results: PagedResult<U>
        ): SliceResponse<T> {
            return SliceResponse(
                content,
                offset,
                limit,
                results.totalElements,
                results.totalPages,
            )
        }
    }
}

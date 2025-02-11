package com.bondary.dto

data class UserChatRequest(
    val title: String,
    val thumbnailId: Long,
    val userIds: List<Long>,
)

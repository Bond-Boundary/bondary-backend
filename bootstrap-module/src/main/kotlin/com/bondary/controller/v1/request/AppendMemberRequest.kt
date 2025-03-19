package com.bondary.controller.v1.request

data class AppendMemberRequest(
    val token: String,
    val introduction: String,
    val schoolName: String,
    val firstMajorName: String,
    val secondaryMajorName: String?,
    val interestArea: List<String>,
    val interestJob: String?,
    val instagram: String?,
    val linkedin: String?,
    val etcLinks: List<String>?,
)

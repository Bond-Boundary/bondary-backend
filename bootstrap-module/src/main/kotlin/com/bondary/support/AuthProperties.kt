package com.bondary.support

enum class AuthProperties(
    val value: String
) {
    AUTHORIZATION_HEADER("Authorization"),
    BEARER("Bearer ")
}
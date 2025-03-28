package com.bondary.support.auth

enum class AuthProperties(
    val value: String
) {
    AUTHORIZATION_HEADER("Authorization"),
    BEARER("Bearer ")
}
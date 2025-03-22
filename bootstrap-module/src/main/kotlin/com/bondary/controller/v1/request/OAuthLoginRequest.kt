package com.bondary.controller.v1.request

data class OAuthLoginRequest(
    val authorizationCode: String,
    val redirectUri: String
)

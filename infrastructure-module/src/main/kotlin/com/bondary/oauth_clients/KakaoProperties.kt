package com.bondary.oauth_clients

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "kakao")
class KakaoProperties(
    val clientId: String,
    val redirectUri: String
)
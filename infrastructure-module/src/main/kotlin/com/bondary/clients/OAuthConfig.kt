package com.bondary.clients

import com.bondary.member.OAuthProvider
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient

@Configuration
@EnableConfigurationProperties(KakaoProperties::class)
class OAuthConfig {
    /**
     * API 요청용
     */
    @Bean
    @Qualifier("kakao")
    fun kakaoClient(): WebClient =
        WebClient.builder()
            .baseUrl("https://kapi.kakao.com")
            .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .build()

    /**
     * 인증 및 토큰 교환용
     */
    @Bean
    @Qualifier("kakaoAuth")
    fun kakaoAuthClient(): WebClient =
        WebClient.builder()
            .baseUrl("https://kauth.kakao.com")
            .defaultHeader("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .build()

    @Bean
    @Qualifier("oAuthManager")
    fun oauthManger(oAuthManagerForKakao: OAuthManagerForKakao): Map<OAuthProvider, OAuthManager> =
        mapOf(OAuthProvider.KAKAO to oAuthManagerForKakao as OAuthManager)
}
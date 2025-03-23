package com.bondary.oauth_clients

import com.bondary.oauth_clients.OAuthManager.OAuthClientResponse
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class OAuthManagerForKakao(
    @Qualifier("kakao") private val kakaoClient: WebClient,
    @Qualifier("kakaoAuth") private val kakaoAuthClient: WebClient,
    private val kakaoProperties: KakaoProperties
) : OAuthManager {
    override fun getAccessToken(request: OAuthManager.AuthorizationCodeRequest): Mono<OAuthManager.AccessTokenResponse> {
        return kakaoAuthClient
            .post()
            .uri("/oauth/token")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(
                BodyInserters.fromFormData("grant_type", "authorization_code")
                    .with("client_id", kakaoProperties.clientId)
                    .with("redirect_uri", request.redirectUri)
                    .with("code", request.authorizationCode)
            )
            .retrieve()
            .bodyToMono(KakaoTokenResponse::class.java)
            .map { response ->
                OAuthManager.AccessTokenResponse(
                    accessToken = response.accessToken,
                    refreshToken = response.refreshToken,
                    expiresIn = response.expiresIn
                )
            }
    }

    override fun getOAuthInfo(request: OAuthManager.OAuthClientRequest): Mono<OAuthClientResponse> {
        return fetchKakaoMemberInfo(request.accessToken)
            .map { kakaoMemberInfo -> mapToClientResponse(kakaoMemberInfo) }
            .switchIfEmpty(Mono.error(RuntimeException("카카오 사용자 정보를 가져오는데 실패했습니다.")))
    }

    private fun fetchKakaoMemberInfo(accessToken: String): Mono<KakaoMemberInfo> {
        return kakaoClient
            .get()
            .uri("/v2/user/me")
            .header("Authorization", "Bearer $accessToken")
            .retrieve()
            .onStatus({it.isError}, { response ->
                response.bodyToMono(String::class.java)
                    .map { errorBody -> RuntimeException("카카오 사용자 정보를 가져오는데 실패했습니다. 상태 코드: ${response.statusCode()}, 오류: $errorBody") }
            })
            .bodyToMono(KakaoMemberInfo::class.java)
    }

    private fun mapToClientResponse(kakaoMemberInfo: KakaoMemberInfo): OAuthClientResponse {
        return OAuthClientResponse(
            name = kakaoMemberInfo.properties?.nickname ?:
            kakaoMemberInfo.kakaoAccount?.profile?.nickname ?:
            "Unknown",
            socialId = kakaoMemberInfo.id,
            profileImage = kakaoMemberInfo.properties?.profileImage ?:
            kakaoMemberInfo.kakaoAccount?.profile?.profileImageUrl ?:
            "",
            email = kakaoMemberInfo.kakaoAccount?.email ?: ""
        )
    }

    data class KakaoTokenResponse(
        @JsonProperty("access_token")
        val accessToken: String,

        @JsonProperty("refresh_token")
        val refreshToken: String? = null,

        @JsonProperty("expires_in")
        val expiresIn: Long = 0L,

        @JsonProperty("token_type")
        val tokenType: String? = null,

        @JsonProperty("scope")
        val scope: String? = null
    )

    data class KakaoMemberInfo(
        val id: String,
        val properties: Properties? = null,
        @JsonProperty("kakao_account")
        val kakaoAccount: KakaoAccount? = null
    )

    data class Properties(
        val nickname: String? = null,
        @JsonProperty("profile_image")
        val profileImage: String? = null,
        @JsonProperty("thumbnail_image")
        val thumbnailImage: String? = null
    )

    data class KakaoAccount(
        val email: String? = null,
        val profile: Profile? = null
    )

    data class Profile(
        val nickname: String? = null,
        @JsonProperty("profile_image_url")
        val profileImageUrl: String? = null
    )
}
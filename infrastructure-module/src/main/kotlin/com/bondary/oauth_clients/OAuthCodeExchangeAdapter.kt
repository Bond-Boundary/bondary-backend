package com.bondary.oauth_clients

import com.bondary.OAuthProvider
import com.bondary.application.member.out.MemberOAuthCodeExchangePort
import com.bondary.support.exception.CoreException
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Repository

@Repository
class OAuthCodeExchangeAdapter(
    private val oauthManager: Map<OAuthProvider, OAuthManager>
) : MemberOAuthCodeExchangePort {
    override suspend fun exchangeAuthCodeForToken(
        provider: OAuthProvider,
        authorizationCode: String,
        redirectUri: String
    ): String {
        val manager = oauthManager[provider]
            ?: throw CoreException.InvalidArgument("해당 provider에 대한 OAuth 정보가 없습니다.")

        return manager.getAccessToken(
            OAuthManager.AuthorizationCodeRequest(
                authorizationCode = authorizationCode,
                redirectUri = redirectUri
            )
        ).awaitSingle().accessToken
    }
}
package com.bondary.oauth_clients

import com.bondary.application.member.OAuthMemberInfo
import com.bondary.application.member.out.MemberOAuthInfoPort
import com.bondary.OAuthProvider
import com.bondary.SocialId
import com.bondary.support.exception.CoreException
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Repository

@Repository
class OAuthAdapter(
    private val oauthManager : Map<OAuthProvider, OAuthManager>
) : MemberOAuthInfoPort {
    override suspend fun getOAuthMemberInfo(oauthProvider: OAuthProvider, accessToken: String): OAuthMemberInfo {
        val oauthManagerForProvider = oauthManager[oauthProvider]
            ?: throw CoreException.InvalidArgument("해당 provider에 대한 OAuth 정보가 없습니다.")

        val response = oauthManagerForProvider.getOAuthInfo(
            OAuthManager.OAuthClientRequest(accessToken)
        ).awaitSingle()

        return OAuthMemberInfo(
            name = response.name,
            email = response.email,
            profileImage = response.profileImage,
            socialId = SocialId(response.socialId),
            oAuthProvider = oauthProvider
        )
    }
}
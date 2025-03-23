package com.bondary.security

import com.bondary.OAuthProvider
import com.bondary.SocialId
import io.jsonwebtoken.Claims
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

interface JwtClaims{

    fun getClaims(): Map<String, Any>{
        val claims = mutableMapOf<String, Any>()
        this::class.memberProperties.forEach {
            claims[it.name] = it.getter.call(this) as Any
        }
        return claims
    }

    companion object{
        inline fun <reified T: JwtClaims> convertFromClaims(claims: Claims): T {
            val claimsMap = claims.toMap()
            val constructor = T::class.primaryConstructor ?: throw IllegalArgumentException("Primary 생성자를 찾을 수 없습니다.")

            val args = constructor.parameters.associateWith {
                val value = claimsMap[it.name]?: throw IllegalArgumentException("Claim을 찾을 수 없습니다.")

                when(it.type.classifier){
                    String::class -> value.toString()
                    Long::class -> value.toString().toLong()
                    OAuthProvider::class -> OAuthProvider.parse(value.toString())
                    JwtTokenType::class -> JwtTokenType.valueOf(value.toString())
                    SocialId::class -> SocialId(value.toString())
                    else -> throw IllegalArgumentException("지원하지 않는 타입입니다: ${it.type.classifier}")
                }
            }
            return constructor.callBy(args)
        }
    }


}
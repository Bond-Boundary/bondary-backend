package com.bondary

import com.bondary.support.exception.CoreException

enum class OAuthProvider {
    KAKAO;

    companion object{
        fun parse(value: String): OAuthProvider {
            return when (value) {
                entries.firstOrNull { it.name == value }?.name -> valueOf(value)
                else -> throw CoreException.InvalidArgument("유효하지 않은 소셜 로그인 제공자입니다.")
            }
        }
    }
}
package com.bondary.support

class AuthMemberAuthentication(
    val memberId: String
) : Authentication<String> {
    override fun getDetails(): String {
        return memberId
    }

    override fun getName(): String {
        return memberId
    }
}

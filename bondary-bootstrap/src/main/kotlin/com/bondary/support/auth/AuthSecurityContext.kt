package com.bondary.support.auth

class AuthSecurityContext<T: Authentication<R>, R>(
    private var authentication: T
) {

    fun getAuthentication(): T {
        return authentication
    }

}
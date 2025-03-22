package com.bondary.support

class AuthSecurityContext<T: Authentication<R>, R>(
    private var authentication: T
) {

    fun getAuthentication(): T {
        return authentication
    }

}
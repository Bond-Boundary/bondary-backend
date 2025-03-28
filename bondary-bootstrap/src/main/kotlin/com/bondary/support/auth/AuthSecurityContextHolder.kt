package com.bondary.support.auth

class AuthSecurityContextHolder {
    companion object {
        private val contextHolder = ThreadLocal<AuthSecurityContext<*, *>>()

        fun getContext(): AuthSecurityContext<*, *> {
            return contextHolder.get()
        }

        fun setContext(context: AuthSecurityContext<*, *>) {
            contextHolder.set(context)
        }

        fun clearContext() {
            contextHolder.remove()
        }
    }
}
package com.bondary.support.auth

import java.security.Principal

interface Authentication<T>: Principal {
    fun getDetails(): T
}
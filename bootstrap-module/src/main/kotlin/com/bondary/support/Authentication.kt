package com.bondary.support

import java.security.Principal

interface Authentication<T>: Principal {
    fun getDetails(): T
}
package com.bondary.application.career.out

import com.bondary.career.Career

interface CareerFunctionPort {
    fun save(career: Career) : String
}
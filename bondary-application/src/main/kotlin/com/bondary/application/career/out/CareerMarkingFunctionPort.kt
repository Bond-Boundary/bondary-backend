package com.bondary.application.career.out

import com.bondary.career.Career

interface CareerMarkingFunctionPort {
    fun marking(career: Career) : String
}
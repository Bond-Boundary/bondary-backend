package com.bondary.application.career.out

import com.bondary.career.Career

interface CareerMarkingRemoveFunctionPort {
    fun remove(career: Career) : String
}
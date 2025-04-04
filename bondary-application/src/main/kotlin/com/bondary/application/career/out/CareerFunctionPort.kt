package com.bondary.application.career.out

import com.bondary.career.Career

interface CareerFunctionPort {
    fun save(career: Career) : String

    fun modify(career: Career) : String

    fun delete(career: Career): String

    fun getCareer(careerId: String, memberId: String): Career
}
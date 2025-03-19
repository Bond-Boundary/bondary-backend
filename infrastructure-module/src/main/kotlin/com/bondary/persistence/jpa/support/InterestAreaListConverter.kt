package com.bondary.persistence.jpa.support

import com.bondary.persistence.jpa.member.entity.InterestArea
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

/**
 * List<InterestArea>와 JSON 문자열 간의 변환을 처리하는 컨버터
 */

@Converter
class InterestAreaListConverter : AttributeConverter<List<InterestArea>, String> {
    private val objectMapper = ObjectMapper()

    override fun convertToDatabaseColumn(attribute: List<InterestArea>?): String? {
        val stringList = attribute?.map { it.name }
        return stringList?.let { objectMapper.writeValueAsString(it) }
    }

    override fun convertToEntityAttribute(dbData: String?): List<InterestArea>? {
        return dbData?.let {
            val stringList = objectMapper.readValue(it, object : TypeReference<List<String>>() {})
            stringList.mapNotNull { enumString ->
                try {
                    InterestArea.valueOf(enumString)
                } catch (e: IllegalArgumentException) {
                    null
                }
            }
        }
    }
}
package com.bondary.persistence.jpa.support

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

/**
 * List<String>과 JSON 문자열 간의 변환을 처리하는 컨버터
 * 모든 데이터베이스와 호환되는 방식으로 JSON 데이터를 저장
 */

@Converter
class StringListConverter : AttributeConverter<List<String>, String> {
    private val objectMapper = ObjectMapper()

    override fun convertToDatabaseColumn(attribute: List<String>?): String? {
        return attribute?.let { objectMapper.writeValueAsString(it) }
    }

    override fun convertToEntityAttribute(dbData: String?): List<String>? {
        return dbData?.let {
            objectMapper.readValue(it, object : TypeReference<List<String>>() {})
        }
    }
}
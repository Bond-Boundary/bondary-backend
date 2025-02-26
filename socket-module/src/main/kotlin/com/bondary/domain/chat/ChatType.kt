package com.bondary.domain.chat

import com.fasterxml.jackson.annotation.JsonCreator

enum class ChatType() {
    COFFEE_CHAT,
    BEAN_CHAT;

    companion object {
        @JsonCreator
        fun from(type: String): ChatType = entries.find { it.name.equals(type, ignoreCase = true) }
            ?: throw IllegalArgumentException("Invalid chat type: $type.")

    }
}

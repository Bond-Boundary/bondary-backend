package com.bondary.domain.message

sealed interface MessagePayload {
    data class TextPayload(
        val content: String
    ) : MessagePayload

    data class ImagePayload(
        val imageUrl: String,
        val thumbnail: String? = null
    ) : MessagePayload

    data class FilePayload(
        val fileUrl: String,
        val fileName: String,
        val fileSize: String
    ) : MessagePayload

    data class SystemPayload(
        val systemMessageType: SystemMessageType,
        val content: String
    ) : MessagePayload
}

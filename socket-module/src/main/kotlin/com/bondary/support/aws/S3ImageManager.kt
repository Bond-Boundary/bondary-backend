package com.bondary.support.aws

import io.awspring.cloud.s3.ObjectMetadata
import io.awspring.cloud.s3.S3Template
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.ByteArrayInputStream
import java.util.*

@Component
class S3ImageManager(
    private val s3Template: S3Template,
    @Value("\${aws.s3.bucket}") private val bucketName: String
) {
    fun uploadFile(
        fileData: ByteArray,
        fileName: String
    ): String {
        val key = generateFileKey(fileName)
        val contentType = getContentType(fileName)

        val inputStream = ByteArrayInputStream(fileData)

        val resource =
            s3Template.upload(
                bucketName,
                key,
                inputStream,
                ObjectMetadata.builder()
                    .contentType(contentType)
                    .acl("public-read")
                    .build(),
            )

        return resource.url.toString()
    }

    private fun generateFileKey(fileName: String): String {
        val uuid = UUID.randomUUID()
        val fileExtension = fileName.substringAfterLast('.', "")
        val sanitizedFileName =
            fileName.substringBeforeLast('.')
                .replace("[^a-zA-Z0-9-_]".toRegex(), "_")

        return "chat/files/$uuid-$sanitizedFileName.$fileExtension"
    }

    private fun getContentType(fileName: String): String =
        when (fileName.substringAfterLast('.', "").lowercase()) {
            "jpg", "jpeg" -> "image/jpeg"
            "png" -> "image/png"
            "gif" -> "image/gif"
            "pdf" -> "application/pdf"
            "doc", "docx" -> "application/msword"
            "xls", "xlsx" -> "application/vnd.ms-excel"
            "txt" -> "text/plain"
            else -> "application/octet-stream"
        }
}

package com.bondary.websocket

import com.bondary.domain.message.Message
import com.bondary.domain.message.MessagePayload
import com.bondary.domain.message.MessageService
import com.bondary.domain.message.MessageType
import com.bondary.support.aws.S3ImageManager
import com.bondary.websocket.dto.WebSocketMessage
import org.springframework.stereotype.Component
import org.springframework.web.socket.BinaryMessage
import org.springframework.web.socket.WebSocketSession
import java.util.concurrent.ConcurrentHashMap

@Component
class ChatFileHandler(
    private val s3ImageManager: S3ImageManager,
    private val messageService: MessageService
) {
    private val uploadSessions = ConcurrentHashMap<String, FileUploadSession>()

    fun prepareFileUpload(session: WebSocketSession, message: WebSocketMessage) {
        val sessionId = createUploadSessionId(session, message)
        uploadSessions[sessionId] = when (message) {
            is WebSocketMessage.FileMessage -> FileUploadSession(
                chatId = message.chatId,
                senderId = message.senderId,
                fileName = message.fileName,
                totalSize = message.fileSize,
                messageType = MessageType.FILE
            )
            is WebSocketMessage.ImageMessage -> FileUploadSession(
                chatId = message.chatId,
                senderId = message.senderId,
                fileName = message.fileName,
                totalSize = message.fileSize,
                messageType = MessageType.IMAGE
            )
            else -> throw IllegalArgumentException("Invalid message type for file upload")
        }
    }

    fun handleFileUpload(session: WebSocketSession, message: BinaryMessage) {
        val sessionId = session.getUserId() ?: return
        val uploadSession = uploadSessions[sessionId] ?: return

        uploadSession.chunks.add(message.payload.array())

        if (uploadSession.isComplete()) {
            val fileData = uploadSession.combineChunks()
            val fileUrl = s3ImageManager.uploadFile(fileData, uploadSession.fileName)

            val domainMessage = createMessage(uploadSession, fileUrl)
            messageService.saveMessage(domainMessage)
            uploadSessions.remove(sessionId)
        }
    }

    private fun createMessage(session: FileUploadSession, fileUrl: String): Message {
        val payload = when (session.messageType) {
            MessageType.FILE -> MessagePayload.FilePayload(
                fileUrl = fileUrl,
                fileName = session.fileName,
                fileSize = session.totalSize.toString()
            )
            MessageType.IMAGE -> MessagePayload.ImagePayload(
                imageUrl = fileUrl,
                thumbnail = null  // 썸네일 생성 로직은 추후 구현
            )
            else -> throw IllegalStateException("Invalid message type: ${session.messageType}")
        }

        return Message(
            chatId = session.chatId,
            senderId = session.senderId,
            messageType = session.messageType,
            payload = payload
        )
    }

    private data class FileUploadSession(
        val chatId: String,
        val senderId: String,
        val fileName: String,
        val totalSize: Long,
        val messageType: MessageType,
        val chunks: MutableList<ByteArray> = mutableListOf()
    ) {
        fun isComplete() = chunks.sumOf { it.size } >= totalSize
        fun combineChunks(): ByteArray = chunks.reduce { acc, bytes -> acc + bytes }
    }

    private fun createUploadSessionId(session: WebSocketSession, message: WebSocketMessage): String =
        "${session.getUserId()}_${message.chatId}_${System.currentTimeMillis()}"

    private fun WebSocketSession.getUserId(): String? =
        attributes["userId"] as? String
}

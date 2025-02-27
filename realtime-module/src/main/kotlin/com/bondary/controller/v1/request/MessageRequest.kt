package com.bondary.controller.v1.request

import com.bondary.model.Message
import com.bondary.model.MessageType

data class MessageRequest(
    val chatId: String,
    val senderId: Long,
    val receiverId: Long,
    val content: String,
    var type: String = "TEXT"
) {
    fun toDocument(): Message = Message(
        chatId = this.chatId,
        senderId = this.senderId,
        receiverId = this.receiverId,
        content = this.content,
        type = MessageType.valueOf(this.type)
    )
}

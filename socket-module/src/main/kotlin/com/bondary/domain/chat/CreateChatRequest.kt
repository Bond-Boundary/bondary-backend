package com.bondary.domain.chat

data class CreateChatRequest(
    val senderId: Long,
    val targetId: Long,
    val chatType: String
) {
    fun toChatType(): ChatType = ChatType.from(chatType)
}

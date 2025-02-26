package com.bondary.domain.userchat

import com.bondary.support.BaseEntity
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.CompoundIndexes
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "user_chats")
@CompoundIndexes(
    CompoundIndex(
        name = "chat_user_idx",
        def = "{'chatId': 1, 'userId': 1}",
        unique = true,
    ),
)
data class UserChat(
    @Id
    val id: String? = null,
    val chatId: String,
    val userId: Long,
    var mute: Boolean = false,
    var displayIdx: String = ""
) : BaseEntity()

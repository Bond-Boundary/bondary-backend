package com.bondary.domain.userchat

import com.bondary.support.BaseEntity
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.CompoundIndexes
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "messages")
@CompoundIndexes(
    CompoundIndex(name = "chat_user_idx", def = "{'chatId': 1, 'userId': 1}"),
)
data class UserChat(
    @Id
    val id: String? = null,
    val chatId: Long,
    val userId: Long,
    val mute: Boolean = false,
    val displayIdx: String = ""
) : BaseEntity()

package com.bondary.domain.chat

import com.bondary.domain.Chat
import com.bondary.domain.message.MessageRepository
import com.bondary.domain.userchat.UserChat
import com.bondary.domain.userchat.UserChatRepository
import com.bondary.support.SliceResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
class ChatService(
    private val chatRepository: ChatRepository,
    private val userChatRepository: UserChatRepository,
    private val messageRepository: MessageRepository
) {
    suspend fun appendChat(
        senderId: String,
        targetId: String,
        chatType: ChatType
    ): String {
        val newChat = Chat(chatType = chatType)
        val appendedChat =
            withContext(Dispatchers.IO) {
                chatRepository.save(newChat)
            }

        val appendedUserChat =
            listOf(
                UserChat(
                    chatId = appendedChat.id?.toLong() ?: -1,
                    userId = senderId.toLong(),
                ),
                UserChat(
                    chatId = appendedChat.id?.toLong() ?: -1,
                    userId = targetId.toLong(),
                ),
            )
        userChatRepository.saveAll(appendedUserChat)
        return appendedChat.id!!
    }

    suspend fun findChats(
        userId: Long,
        key: String? = null,
        size: Int = 20
    ): SliceResult<ChatResponse> {
        val findsUserChat =
            if (key != null) {
                withContext(Dispatchers.IO) {
                    userChatRepository.findByUserIdAndDisplayIdxLessThanOrderByDisplayIdxDesc(
                        userId = userId,
                        displayIdx = key,
                        size = size + 1,
                    )
                }
            } else {
                withContext(Dispatchers.IO) {
                    userChatRepository.findByUserIdOrderByDisplayIdxDesc(
                        userId = userId,
                        size = size + 1,
                    )
                }
            }

        val hasNext = findsUserChat.size > size
        val currentPage = if (hasNext) findsUserChat.dropLast(1) else findsUserChat

        if (currentPage.isEmpty()) {
            return SliceResult(
                content = emptyList(),
                hasNext = false,
                nextKey = null,
            )
        }

        val chatIds = currentPage.map { it.chatId.toString() }
        val findChats = chatRepository.findAllById(chatIds)

        val response =
            findChats.mapNotNull { chat ->
                val userChat = currentPage.find { it.chatId.toString() == chat.id } ?: return@mapNotNull null
                ChatResponse(
                    chatId = chat.id?.toLong() ?: -1,
                    chatType = chat.chatType.toString(),
                    displayIdx = userChat.displayIdx,
                    lastMessage = null,
                    updatedAt = chat.updatedAt,
                )
            }

        return SliceResult(
            content = response,
            hasNext = hasNext,
            nextKey = if (hasNext) currentPage.last().displayIdx else null,
        )
    }
}

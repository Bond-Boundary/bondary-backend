package com.bondary.domain.chat

import com.bondary.domain.Chat
import com.bondary.domain.message.*
import com.bondary.domain.userchat.UserChat
import com.bondary.domain.userchat.UserChatRepository
import com.bondary.external.RelationshipClient
import com.bondary.support.SliceResult
import com.bondary.websocket.WebSocketSessionManager
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service

@Service
class ChatService(
    private val chatRepository: ChatRepository,
    private val userChatRepository: UserChatRepository,
    private val messageRepository: MessageRepository,
    private val relationshipClient: RelationshipClient,
    private val messageService: MessageService,
    private val webSocketSessionManager: WebSocketSessionManager
) {
    suspend fun createChat(
        request: CreateChatRequest
    ): String {
        val newChat = Chat(chatType = request.toChatType())
        val savedChat = chatRepository.save(newChat)

        if (request.toChatType() == ChatType.BEAN_CHAT) {
            createBeanChat(savedChat, request.senderId)
        }

        val appendedUserChat = listOf(
            UserChat(
                chatId = savedChat.id ?: "-1",
                userId = request.senderId,
            ),
            UserChat(
                chatId = savedChat.id ?: "-1",
                userId = request.targetId,
            )
        )

        userChatRepository.saveAll(appendedUserChat)
        return savedChat.id!!
    }

    suspend fun findChats(
        userId: Long,
        key: String? = null,
        size: Int = 20
    ): SliceResult<ChatResponse> {
        val findUserChats =
            if (key != null) {
                userChatRepository.findByUserIdAndDisplayIdxLessThanOrderByDisplayIdxDesc(
                    userId = userId,
                    displayIdx = key,
                    size = size + 1
                )
            } else {
                userChatRepository.findByUserIdOrderByDisplayIdxDesc(

                    userId = userId,
                    size = size + 1
                )
            }

        val userChatsList = findUserChats.toList()
        val hasNext = userChatsList.size > size
        val currentPage = if (hasNext) userChatsList.dropLast(1) else userChatsList

        if (currentPage.isEmpty()) {
            return SliceResult(
                content = emptyList(),
                hasNext = false,
                nextKey = null
            )
        }

        val chatIds = currentPage.map { it.chatId }
        val findChats = chatRepository.findAllById(chatIds)

        val response = findChats.mapNotNull { chat ->
            val userChat = currentPage.find { it.chatId == chat.id } ?: return@mapNotNull null

//            // Flow를 List로 변환하여 첫 번째 매칭되는 항목 찾기
//            val otherParticipant = userChatRepository.findByChatId(userChat.chatId)
//                .toList()
//                .find { it.userId != userId } ?: return@mapNotNull null
//            val relationShip = relationshipClient.checkRelationship(
//                userId = userId,
//                targetId = otherParticipant.userId
//            )

            val relationShip = try {
                relationshipClient.checkRelationship(
                    userId = userId, targetId = userChat.userId
                )
            } catch (e: Exception) {
                println("관계 API 호출 실패: ${e.message}")
                DummyRelationshipResponse("unknown")
            }

            ChatResponse(
                chatId = chat.id ?: "-1",
                chatType = chat.chatType.toString(),
                displayIdx = userChat.displayIdx,
                lastMessage = null,
                updatedAt = chat.updatedAt,
//                relationshipType = relationShip.type.toString()
                relationshipType = relationShip.toString()
            )
        }

        return SliceResult(
            content = response.toList(),
            hasNext = hasNext,
            nextKey = if (hasNext) currentPage.last().displayIdx else null
        )
    }

    suspend fun leaveChat(
        chatId: String,
        userId: Long
    ) {
        userChatRepository.deleteByChatIdAndUserId(chatId, userId)
        webSocketSessionManager.removeSession(userId)
    }

    suspend fun muteChat(
        chatId: String,
        userId: Long,
        mute: Boolean
    ) {
        val findUserChats = userChatRepository.findByChatIdAndUserId(chatId, userId)
        findUserChats.mute = mute
        userChatRepository.save(findUserChats)
    }

    private suspend fun createBeanChat(savedChat: Chat, senderId: Long) {
        val systemMessage = Message(
            chatId = savedChat.id ?: "-1",
            senderId = senderId,
            messageType = MessageType.SYSTEM,
            payload = MessagePayload.SystemPayload(
                systemMessageType = SystemMessageType.BEAN_CHAT_REQUEST,
                content = "${senderId}님이 처음으로 대화를 요청했어요!"
            )
        )
        messageService.saveAndSendMessage(systemMessage)
    }
}

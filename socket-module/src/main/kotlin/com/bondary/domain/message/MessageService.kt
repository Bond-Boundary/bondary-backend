package com.bondary.domain.message

import com.bondary.domain.Chat
import com.bondary.domain.chat.ChatRepository
import com.bondary.domain.userchat.UserChatRepository
import com.bondary.support.SliceResult
import com.bondary.websocket.WebSocketSessionManager
import com.bondary.websocket.dto.WebSocketMessage
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.socket.TextMessage

@Service
class MessageService(
    private val messageRepository: MessageRepository,
    private val userChatRepository: UserChatRepository,
    private val chatRepository: ChatRepository,
    private val webSocketSessionManager: WebSocketSessionManager,
    private val objectMapper: ObjectMapper,
    private val messageCreator: MessageCreator
) {
    private val logger = LoggerFactory.getLogger(MessageService::class.java)

    suspend fun markMessagesAsRead(chatId: String, userId: Long) {
        // 로그 추가
        logger.debug("사용자 ${userId}가 채팅방 ${chatId}의 메시지 읽음 처리 시작")

        val unreadMessages = withContext(Dispatchers.IO) {
            messageRepository.findUnreadMessages(chatId, userId)
        }

        // 읽지 않은 메시지가 있는지 로그로 확인
        logger.debug("읽지 않은 메시지 개수: ${unreadMessages.size}")

        if (unreadMessages.isNotEmpty()) {
            val messageIds = unreadMessages.mapNotNull { it.id }
            logger.debug("읽음 처리할 메시지 IDs: ${messageIds}")

            withContext(Dispatchers.IO) {
                messageRepository.markMessagesAsRead(messageIds)
            }

            val readMessage = WebSocketMessage.ReadMessage(
                chatId = chatId,
                senderId = userId,
                messageIds = messageIds
            )

            // 로그에서 readMessage의 내용을 확인
            logger.debug("생성된 READ 메시지: ${objectMapper.writeValueAsString(readMessage)}")

            withContext(Dispatchers.IO) {
                userChatRepository.findByChatId(chatId)
                    .collect { userChat ->
                        val session = webSocketSessionManager.getSession(userChat.userId)
                        if (session != null) {
                            logger.debug("사용자 ${userChat.userId}에게 READ 메시지 전송")
                            session.sendMessage(
                                TextMessage(objectMapper.writeValueAsString(readMessage))
                            )
                        } else {
                            logger.debug("사용자 ${userChat.userId}의 WebSocket 세션을 찾을 수 없습니다")
                        }
                    }
            }
        } else {
            logger.debug("읽지 않은 메시지가 없습니다.")
        }
    }

    suspend fun countUnreadMessages(
        chatId: String,
        userId: Long
    ): Long {
        return withContext(Dispatchers.IO) {
            messageRepository.countUnreadMessages(chatId, userId) ?: 0L
        }
    }

    suspend fun saveAndSendMessage(message: Message) {
        val savedMessage =
            withContext(Dispatchers.IO) {
                messageRepository.save(message)
            }
        updateChatState(savedMessage)
        broadcastMessage(savedMessage)
    }

    suspend fun findMessages(
        chatId: String,
        key: String?,
        size: Int
    ): SliceResult<MessageResponse> {
        val messages = if (key != null) {
            messageRepository.findByChatIdAndIdLessThanOrderByTimestampDesc(
                chatId = chatId,
                id = key,
                size = size + 1
            )
        } else {
            messageRepository.findByChatIdOrderByTimestampDesc(
                chatId = chatId,
                size = size + 1
            )
        }

        val messagesList = messages.toList()
        val hasNext = messagesList.size > size

        // 조회된 메시지 리스트에서 마지막 메시지 ID를 다음 요청 키로 사용
        val nextKey = if (hasNext) messagesList.last().id else null

        return SliceResult(
            content = messagesList.map { MessageResponse.of(it) },
            hasNext = hasNext,
            nextKey = nextKey
        )
    }

    private suspend fun updateChatState(message: Message) {
        coroutineScope {
            launch { updateParticipantDisplayIdx(message) }
            launch { updateLastMessage(message) }
        }
    }

    private suspend fun updateParticipantDisplayIdx(message: Message) {
        userChatRepository.findByChatId(message.chatId)
            .collect { userChat ->
                userChat.displayIdx = generateDisplayIdxFromMessageId(message.id!!)
                userChatRepository.save(userChat)
            }
    }

    private suspend fun updateLastMessage(message: Message) {
        val lastMessage =
            Chat.LastMessage(
                content = getMessageContent(message),
                senderId = message.senderId,
                timestamp = message.timestamp,
            )

        val chat = withContext(Dispatchers.IO) {
                chatRepository.findById(message.chatId)
            }

        chat?.let {
            it.lastMessage = lastMessage // 직접 수정
            withContext(Dispatchers.IO) {
                chatRepository.save(it)
            }
        }
    }

    private suspend fun broadcastMessage(message: Message) {
        val messageEvent = messageCreator.createMessageEvent(message)
        sendToParticipants(message.chatId, messageEvent)
    }

    private suspend fun sendToParticipants(
        chatId: String,
        messageEvent: WebSocketMessage
    ) {
        withContext(Dispatchers.IO) {
            userChatRepository.findByChatId(chatId)
                .collect { userChat ->
                    webSocketSessionManager.getSession(userChat.userId)?.sendMessage(
                        TextMessage(objectMapper.writeValueAsString(messageEvent)),
                    )
                }
        }
    }

    private fun getMessageContent(message: Message): String =
        when (val payload = message.payload) {
            is MessagePayload.TextPayload -> payload.content
            is MessagePayload.SystemPayload -> payload.content
            is MessagePayload.FilePayload -> payload.fileName
            is MessagePayload.ImagePayload -> payload.imageUrl
            is MessagePayload.ReadPayload -> payload.content
        }

    private fun generateDisplayIdxFromMessageId(messageId: String): String {
        // messageId를 기반으로 정렬 가능한 displayIdx 생성
        // 예: messageId + 타임스탬프를 조합하여 유니크한 정렬 키 생성
        return "${messageId}_${System.currentTimeMillis()}"
    }
}

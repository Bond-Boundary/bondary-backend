package com.bondary.service

import com.bondary.model.Chat
import com.bondary.model.UserChat
import com.bondary.repository.UserChatRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactive.awaitFirst
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class UserChatCreator(
    private val userChatRepository: UserChatRepository,
) {
    private val logger = LoggerFactory.getLogger(UserChatCreator::class.java)

    suspend fun createUserEntries(
        chat: Chat,
        senderId: Long,
        receiverId: Long,
    ) = coroutineScope {
        val chatId = chat.id!!
        val createEntry: suspend (Long) -> Unit = { userId ->
            userChatRepository.save(
                UserChat(
                    userId = userId,
                    chatId = chatId,
                    chatTitle = "수신자 이름",    // 실제 수신자 이름으로 추후 수정 예정
                    displayIndex = chat.createdAt.toEpochMilli().toString(), // 이후에는 displayIdx 가 최근 message id로 update!!
                    updatedAt = Instant.now(),
                ),
            ).awaitFirst()
        }
        val deferredBySender = async { createEntry(senderId) }
        val deferredByReceiver = async { createEntry(receiverId) }
        deferredBySender.await()
        deferredByReceiver.await()
        logger.info("UserChat 엔트리 생성 완료: $senderId, $receiverId")
    }
}

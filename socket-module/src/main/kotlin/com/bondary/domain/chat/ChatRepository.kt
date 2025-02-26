package com.bondary.domain.chat

import com.bondary.domain.Chat
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ChatRepository : CoroutineCrudRepository<Chat, String>


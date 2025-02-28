package com.bondary.repository

import com.bondary.model.Chat
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface ChatRepository : ReactiveMongoRepository<Chat, String>, ChatRepositoryCustom

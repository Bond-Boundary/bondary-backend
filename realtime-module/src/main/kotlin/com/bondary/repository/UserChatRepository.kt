package com.bondary.repository

import com.bondary.model.UserChat
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface UserChatRepository : ReactiveMongoRepository<UserChat, String>, UserChatRepositoryCustom

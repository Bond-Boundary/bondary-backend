package com.bondary.repository

import com.bondary.model.Message
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface MessageRepository : ReactiveMongoRepository<Message, String>

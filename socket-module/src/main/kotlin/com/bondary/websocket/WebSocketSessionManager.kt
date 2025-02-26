package com.bondary.websocket

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketSession
import java.util.concurrent.ConcurrentHashMap

@Component
class WebSocketSessionManager {
    private val sessions = ConcurrentHashMap<Long, WebSocketSession>()
    private val logger = LoggerFactory.getLogger(WebSocketSessionManager::class.java)

    fun addSession(
        userId: Long,
        session: WebSocketSession
    ) {
        sessions[userId] = session
    }

    fun removeSession(userId: Long) {
        sessions.remove(userId)
    }

    fun getSession(userId: Long): WebSocketSession? {
        return sessions[userId]
    }

    fun getAllSessions(): Map<Long, WebSocketSession> {
        return sessions.toMap()
    }
}

package com.bondary.websocket

import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketSession
import java.util.concurrent.ConcurrentHashMap

@Component
class WebSocketSessionManager {
    private val sessions = ConcurrentHashMap<Long, WebSocketSession>()

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

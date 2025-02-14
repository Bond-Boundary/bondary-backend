package com.bondary.websocket

import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketSession
import java.util.concurrent.ConcurrentHashMap

@Component
class WebSocketSessionManager {
    private val sessions = ConcurrentHashMap<String, WebSocketSession>()

    fun addSession(userId: String, session: WebSocketSession) {
        sessions[userId] = session
    }

    fun removeSession(userId: String) {
        sessions.remove(userId)
    }

    fun getSession(userId: String): WebSocketSession? {
        return sessions[userId]
    }
}

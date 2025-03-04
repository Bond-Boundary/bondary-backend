package com.bondary.service

import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketSession
import java.util.concurrent.ConcurrentHashMap

@Component
class SessionRegistry(
    private val sessions: ConcurrentHashMap<String, WebSocketSession> = ConcurrentHashMap()
) {
    fun setUserSession(
        userId: Long,
        session: WebSocketSession,
    ) {
        sessions["user:session:$userId"] = session
    }

    fun getUserSession(userId: Long): WebSocketSession? {
        return sessions["user:session:$userId"]
    }

    fun removeUserSession(userId: Long): WebSocketSession? {
        return sessions.remove("user:session:$userId")
    }

    fun isUserOnline(userId: Long): Boolean {
        return getUserSession(userId)?.isOpen == true
    }
}

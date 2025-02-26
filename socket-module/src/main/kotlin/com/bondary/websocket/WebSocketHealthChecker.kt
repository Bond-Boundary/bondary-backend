package com.bondary.websocket

import jakarta.annotation.PreDestroy
import kotlinx.coroutines.*
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.WebSocketSession
import java.time.Duration
import java.time.LocalDateTime

@Component
class WebSocketHealthChecker(
    private val serverLocationManager: ServerLocationManager,
    private val webSocketSessionManager: WebSocketSessionManager
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val maxPongDelay = Duration.ofMinutes(1)

    @Scheduled(fixedRate = 30000)
    fun checkConnectionHealth() {
        val findSessions = webSocketSessionManager.getAllSessions()
        findSessions.forEach { (userId, session) ->
            scope.launch {
                checkUserConnection(userId, session)
            }
        }
    }

    private suspend fun checkUserConnection(
        userId: Long,
        session: WebSocketSession
    ) {
        val lastPongTime = serverLocationManager.getLastPongTime(userId)
        if (lastPongTime != null) {
            val timeSinceLastPong = Duration.between(lastPongTime, LocalDateTime.now())
            if (timeSinceLastPong > maxPongDelay) {
                session.close(CloseStatus.SESSION_NOT_RELIABLE)
                webSocketSessionManager.removeSession(userId)
            }
        }
    }

    @PreDestroy
    fun shutdown() {
        scope.cancel()
    }

}

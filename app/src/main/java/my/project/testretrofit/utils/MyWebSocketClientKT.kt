package my.project.testretrofit.utils

import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI

class MyWebSocketClientKT(serverUri: String?) : WebSocketClient(URI(serverUri)) {
    override fun onOpen(handshakedata: ServerHandshake) {
        // WebSocket соединение открыто
    }

    override fun onMessage(message: String) {
        println("onMessage: $message")
    }

    override fun onClose(code: Int, reason: String, remote: Boolean) {
        // WebSocket соединение закрыто
    }

    override fun onError(ex: Exception) {
        // Произошла ошибка
    }
}
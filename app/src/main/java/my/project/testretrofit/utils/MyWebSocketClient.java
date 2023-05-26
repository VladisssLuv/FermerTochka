package my.project.testretrofit.utils;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class MyWebSocketClient extends WebSocketClient {
    public MyWebSocketClient(String serverUri) throws URISyntaxException {
        super(new URI(serverUri));
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        // WebSocket соединение открыто
    }

    @Override
    public void onMessage(String message) {
        System.out.println("onMessage: " + message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        // WebSocket соединение закрыто
    }

    @Override
    public void onError(Exception ex) {
        // Произошла ошибка
    }
}


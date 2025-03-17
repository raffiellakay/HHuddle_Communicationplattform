package websocket.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.net.http.WebSocket;

@Component
@RequiredArgsConstructor
@Slf4j

public class WebSocketEventListener {
    @EventListener
    public void HandleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        //TODO to be implemented later on
    }
}

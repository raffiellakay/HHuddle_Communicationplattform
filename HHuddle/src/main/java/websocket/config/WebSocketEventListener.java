package websocket.config;

import com.knoettner.hhuddle.models.ChatMessage;
import com.knoettner.hhuddle.models.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.net.http.WebSocket;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.data.util.TypeUtils.type;

@Component
@RequiredArgsConstructor
@Slf4j

public class WebSocketEventListener {

    private final SimpMessagingTemplate template;
    private static final Map<String, String> activeUsers = new CurrentHashMap<>();
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = headerAccessor.getUser() != null ? headerAccessor.getUser().getName() : "Unknown";
        log.info("User disconnected: " + username);
        log.info("User:" + username);
        log.info("Disconnected from: " + username);
        activeUsers.put(headerAccessor.getSessionId(), username);
    }

    @EventListener
    public void handleWebSocketDisconnectListener (SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        String username = activeUsers.remove(sessionId());

        if (username != null){
            log.info ("User disconnected: " + username);
            ChatMessage message = new ChatMessage();
            message.setType(MessageType.LEAVE);
            message.setSender(username);
            template.convertAndSend("/topic/public",message);}}}

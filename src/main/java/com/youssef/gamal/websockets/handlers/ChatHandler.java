package com.youssef.gamal.websockets.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youssef.gamal.websockets.enums.ChatMessageType;
import com.youssef.gamal.websockets.models.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class ChatHandler implements WebSocketHandler {

    private final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final Map<String, String> sessionUsernameMap = new ConcurrentHashMap<>();
    @Autowired private ObjectMapper objectMapper;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.put(session.getId(), session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        ChatMessage chatMessage = objectMapper.readValue(message.getPayload().toString(), ChatMessage.class);
        if(chatMessage.getType() == ChatMessageType.JOIN) {
            // set the header username to use it later when we close session
            // then system brodcast message to all users that the user has leaved the chat
            sessionUsernameMap.put(session.getId(), chatMessage.getSender());
            ChatMessage joinChatMessage = ChatMessage.builder()
                    .content(chatMessage.getSender() + " has joined")
                    .sender("System")
                    .type(ChatMessageType.JOIN)
                    .build();
            brodCaseMessageToAllConnectedSessions(joinChatMessage);
        }
        if(chatMessage.getType() == ChatMessageType.CHAT) {
            brodCaseMessageToAllConnectedSessions(chatMessage);
        }
    }

    public void brodCaseMessageToAllConnectedSessions(ChatMessage chatMessage) throws IOException {
        String message = objectMapper.writeValueAsString(chatMessage);
        for (WebSocketSession session : sessions.values()) {
            try {
                if (session != null && session.isOpen()) {
                    session.sendMessage(new TextMessage(message));
                } else {
                    log.info("Session {} is not open", session.getId());
                }
            } catch (IOException e) {
                log.error("Error sending message to session {}: {}", session.getId(), e.getMessage());
            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.info("Exception occured: {} on session: {}", exception.getMessage(), session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        sessions.remove(session.getId());
        ChatMessage leaveChatMessage = ChatMessage.builder()
                .content(sessionUsernameMap.get(session.getId()) + " has left")
                .type(ChatMessageType.LEAVE)
                .sender("System")
                .build();
        brodCaseMessageToAllConnectedSessions(leaveChatMessage);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}

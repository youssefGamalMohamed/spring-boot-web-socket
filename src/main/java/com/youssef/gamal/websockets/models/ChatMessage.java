package com.youssef.gamal.websockets.models;


import com.youssef.gamal.websockets.enums.ChatMessageType;
import lombok.Data;

@Data
public class ChatMessage {
    private String content;
    private String sender;
    private ChatMessageType type;
}

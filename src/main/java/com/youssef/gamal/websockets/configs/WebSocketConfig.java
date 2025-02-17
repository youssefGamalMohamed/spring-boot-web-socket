package com.youssef.gamal.websockets.configs;

import com.youssef.gamal.websockets.handlers.AdditionHandler;
import com.youssef.gamal.websockets.handlers.ChatHandler;
import com.youssef.gamal.websockets.handlers.TutorialHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(tutorialHandler(), "/tutorial")
                .addHandler(additionHandler(), "/addition")
                .addHandler(chatHandlerBean(), "/chat")
                .setAllowedOrigins("*");

    }

    @Bean
    WebSocketHandler tutorialHandler() {
        return new TutorialHandler();
    }

    @Bean
    WebSocketHandler additionHandler() {
        return new AdditionHandler();
    }

    @Bean
    WebSocketHandler chatHandlerBean() {
        return new ChatHandler();
    }
}

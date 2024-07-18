package com.youssef.gamal.websockets.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youssef.gamal.websockets.models.AdditionMessageReq;
import com.youssef.gamal.websockets.models.AdditionMessageResp;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.*;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class AdditionHandler implements WebSocketHandler {


    private ObjectMapper objectMapper;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Connection established on session: {}", session.getId());
    }


    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String payload = (String) message.getPayload();
        AdditionMessageReq additionMessageReq = objectMapper.readValue(payload, AdditionMessageReq.class);
        log.info("additionMessageReq: {}", additionMessageReq);

        int n = additionMessageReq.getNumber();
        for(int i = 0 ; i < additionMessageReq.getIterations() ; i++) {
            n += additionMessageReq.getAddedValue();
            AdditionMessageResp additionMessageResp = AdditionMessageResp.builder()
                    .result(n)
                    .build();
            String additionMessageRespString = objectMapper.writeValueAsString(additionMessageResp);
            log.info("additionMessageResp: {}", additionMessageRespString);
            session.sendMessage(new TextMessage(additionMessageRespString));
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.info("Exception occured: {} on session: {}", exception.getMessage(), session.getId());

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.info("Connection closed on session: {} with status: {}", session.getId(), closeStatus.getCode());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}

package com.example.chatserver.chat.config;


import org.hibernate.annotations.ConcreteProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

//connect로 웹소켓 연결요청이 들어왔을때 이를 처리할 클래스
@Component
public class SimpleWebSocketHandler extends TextWebSocketHandler {
    // thread safe한 set을 사용
    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet(); // 동시에 몰려들어와도 이 서버에 연결을 맺어도 문제가 없다.
    @Override // 연결이 되면 어떻할건데?
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("Connected to " + session.getId());
    }

   // 사용자한테 메세지를 보내주는 메서드
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("Received: " + payload);
        for(WebSocketSession s : sessions){
            if (s.isOpen()){
                s.sendMessage(new TextMessage(payload));
            }
        }
    }




    @Override  // 연결을 끊어주는 메서드
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("disconnected from " + session.getId());
    }



}

package com.example.chatserver.chat.controller;


import com.example.chatserver.chat.dto.ChatMessageReqDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
public class StompController {

    private final SimpMessageSendingOperations messageTemplate;

    public StompController(SimpMessageSendingOperations messageTemplate) {
        this.messageTemplate = messageTemplate;
    }
//    ////    방법1.MessageMapping(수신)과 SenTo(topic에 메시지전달)한꺼번에 처리
//    @MessageMapping("/{roomId}")
//    @SendTo("/topic/{roomId}") //해당 roomId에 메세지를 발행하여 구독중인 클라이언트에게 메세지 전송
//////    DestinationVariable : @MessageMapping 어노테이션으로 정의된 Websocket Controller 내에서만 사용
//    public String sendMessage(@DestinationVariable Long roomId, String message){
//        System.out.println(message);
//
//        return message;
//    }

    //    방법2.MessageMapping어노테이션만 활용.
    @MessageMapping("/{roomId}")
    public void sendMessage(@DestinationVariable Long roomId, ChatMessageReqDto chatMessageReqDto)  {
        System.out.println(chatMessageReqDto.getMessage());
        messageTemplate.convertAndSend("/topic/"+roomId, chatMessageReqDto);

    }


}

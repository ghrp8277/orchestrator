package com.example.orchestrator.controller;

import com.example.orchestrator.dto.socket.MessageDto;
import com.example.orchestrator.dto.socket.OutputMessageDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {
    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public OutputMessageDto send(MessageDto message) {
        return new OutputMessageDto(message.getFrom(), message.getText());
    }
}

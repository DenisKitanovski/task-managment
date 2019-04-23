package com.example.springwebproektna.controller;

import com.example.springwebproektna.domains.MessageDetails;
import com.example.springwebproektna.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    private final SimpMessagingTemplate template;
    private MessageService messageService;

    @Autowired
    public MessageController(SimpMessagingTemplate template, MessageService messageService) {
        this.template = template;
        this.messageService = messageService;
    }

    @MessageMapping("/chat/rooms/{room}")
    public void send(@DestinationVariable String room, MessageDetails message) throws Exception {
        messageService.handleNewMessage(room, message);
        template.convertAndSend("/topic/rooms/" + room, message);
    }
}

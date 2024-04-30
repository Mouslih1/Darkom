package org.example.chatservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.chatservice.dto.ChatDto;
import org.example.chatservice.dto.MessageDto;
import org.example.chatservice.dto.NotificationDto;
import org.example.chatservice.service.IChatService;
import org.example.chatservice.service.IMessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class ChatController {

    private final IChatService iChatService;
    private final IMessageService iMessageService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat")
    public void processMessage(@Payload MessageDto messageDto)
    {
        MessageDto saved = iMessageService.save(messageDto);
        simpMessagingTemplate.convertAndSendToUser(
                String.valueOf(messageDto.getReceivedId()),
                "/queue/messages",
                new NotificationDto(
                        saved.getId(),
                        saved.getSenderId()
                )
        );
    }


    @GetMapping("/{receivedId}/chat")
    public ResponseEntity<ChatDto> getChat(
            @RequestHeader("id") Long senderId,
            @PathVariable Long receivedId)
    {
        return new ResponseEntity<>(iChatService.getChat(senderId, receivedId), HttpStatus.OK);
    }

    @GetMapping("/{receivedId}/count")
    public ResponseEntity<Integer> countNewMessages(
            @RequestHeader("id") Long senderId,
            @PathVariable Long receivedId
    ){
        return new ResponseEntity<>(iMessageService.countNewMessages(
                senderId,
                receivedId
        ), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageDto> byId(@PathVariable Long id)
    {
        return new ResponseEntity<>(iMessageService.byId(id), HttpStatus.OK);
    }
}

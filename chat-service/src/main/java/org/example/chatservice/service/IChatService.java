package org.example.chatservice.service;

import org.example.chatservice.dto.ChatDto;

public interface IChatService {

    ChatDto getChat(Long senderId, Long receivedId);
}

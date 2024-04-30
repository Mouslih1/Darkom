package org.example.chatservice.service;

import org.example.chatservice.dto.MessageDto;
import org.example.chatservice.entities.enums.StatusMessage;

public interface IMessageService {

    MessageDto save(MessageDto messageDto);
    int countNewMessages(Long senderId, Long ReceivedId);
    void updateStatusMessage(
            Long senderId,
            Long received
    );

    MessageDto byId(Long id);
}

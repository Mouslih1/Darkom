package org.example.chatservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.chatservice.dto.MessageDto;
import org.example.chatservice.entities.Message;
import org.example.chatservice.entities.enums.StatusMessage;
import org.example.chatservice.exception.NotFoundException;
import org.example.chatservice.repository.IMessageRepository;
import org.example.chatservice.service.IMessageService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService implements IMessageService {

    private final IMessageRepository iMessageRepository;
    private final ModelMapper modelMapper;
    private static final String MESSAGE_NOT_FOUND = "Message not found with this id : ";

    @Override
    public MessageDto save(MessageDto messageDto)
    {
        messageDto.setStatusMessage(StatusMessage.NEW);
        Message message = iMessageRepository.save(modelMapper.map(messageDto, Message.class));
        return modelMapper.map(message, MessageDto.class);
    }

    @Override
    public int countNewMessages(
            Long senderId,
            Long receivedId
    )
    {
        return iMessageRepository.countMessageBySenderIdAndReceivedIdAndStatusMessage(
                senderId,
                receivedId,
                StatusMessage.NEW
        );
    }

    @Override
    public void updateStatusMessage(
            Long senderId,
            Long received
    )
    {
        List<Message> messages = iMessageRepository.findBySenderIdAndReceivedIdAndStatusMessage(
                senderId,
                received,
                StatusMessage.NEW
        );
        for (Message message : messages)
        {
            message.setStatusMessage(StatusMessage.DEJAVU);
            iMessageRepository.save(message);
        }
    }

    @Override
    public MessageDto byId(Long id)
    {
        Message message = iMessageRepository.findById(id).orElseThrow(() -> new NotFoundException(MESSAGE_NOT_FOUND + id));
        updateStatusMessage(message.getSenderId(), message.getReceivedId());
        return modelMapper.map(message, MessageDto.class);
    }
}

package org.example.chatservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.chatservice.dto.ChatDto;
import org.example.chatservice.entities.Chat;
import org.example.chatservice.repository.IChatRepository;
import org.example.chatservice.service.IChatService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService implements IChatService {

    private final ModelMapper modelMapper;
    private final IChatRepository iChatRepository;
    private final MessageService messageService;

    @Override
    public ChatDto getChat(Long senderId, Long receivedId)
    {
        Optional<Chat> chat = iChatRepository.findBySenderIdAndReceivedId(senderId, receivedId);
        ChatDto chatDto;

        if(chat.isPresent())
        {
            chatDto =  modelMapper.map(chat, ChatDto.class);
            if(!chatDto.getMessages().isEmpty())
            {
                messageService.updateStatusMessage(senderId, receivedId);
            }
        }else {
            Chat chatSaved = iChatRepository.save(Chat.builder().senderId(senderId).receivedId(receivedId).build());
            chatDto = modelMapper.map(chatSaved, ChatDto.class);
        }

        return chatDto;
    }
}

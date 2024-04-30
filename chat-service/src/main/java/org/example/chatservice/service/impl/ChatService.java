package org.example.chatservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.chatservice.dto.ChatDto;
import org.example.chatservice.entities.Chat;
import org.example.chatservice.repository.IChatRepository;
import org.example.chatservice.service.IChatService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
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
        System.out.println("sender id :" + senderId);
        System.out.println("received id : " + receivedId);
        Optional<Chat> chat = iChatRepository.findBySenderIdAndReceivedId(senderId, receivedId);

        System.out.println("chat get : " + chat);
        ChatDto chatDto = new ChatDto();

        if(chat.isPresent())
        {
            System.out.println(chat);
           // chatDto =  modelMapper.map(chat, ChatDto.class);
            chatDto.setId(chat.get().getId());
            chatDto.setMessages(chat.get().getMessages());
            chatDto.setCreatedAt(chat.get().getCreatedAt());
            chatDto.setReceivedId(chat.get().getReceivedId());
            chatDto.setSenderId(chat.get().getSenderId());


            System.out.println(chatDto);
            if(chatDto.getMessages() != null)
            {
                messageService.updateStatusMessage(senderId, receivedId);
            }
        }else {
            Chat chatSaved = iChatRepository.save(Chat.builder().senderId(senderId).receivedId(receivedId).createdAt(new Date(System.currentTimeMillis())).build());
            chatDto = modelMapper.map(chatSaved, ChatDto.class);
        }

        return chatDto;
    }
}

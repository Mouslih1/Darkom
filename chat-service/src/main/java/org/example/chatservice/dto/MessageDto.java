package org.example.chatservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.chatservice.entities.enums.StatusMessage;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDto {

    private Long id;
    private Long senderId;
    private Long receivedId;
    private String content;
    private StatusMessage statusMessage;
    private LocalDateTime createdAt;
}

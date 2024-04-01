package com.example.notificationservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {

    private Long id;
    private String message;
    private Long receivedId;
    private String senderUsername;
    private Long agenceId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean seen = Boolean.FALSE;
}

package com.example.evenementservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvenementProducerDto {

    private Long relatedId;
    private String message;
    private String senderUsername;
    private Long userCreateNotification;
    private Long receivedId;
    private Long agenceId;
    private String token;
}

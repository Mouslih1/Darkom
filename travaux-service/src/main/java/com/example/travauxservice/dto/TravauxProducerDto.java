package com.example.travauxservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TravauxProducerDto {

    private Long relatedId;
    private String message;
    private String senderUsername;
    private Long receivedId;
    private Long agenceId;
    private String token;
}

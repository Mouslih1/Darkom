package com.example.plainte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlainteProducerDto {

    private Long relatedId;
    private String message;
    private String senderUsername;
    private Long receivedId;
    private Long agenceId;
    private String token;
}

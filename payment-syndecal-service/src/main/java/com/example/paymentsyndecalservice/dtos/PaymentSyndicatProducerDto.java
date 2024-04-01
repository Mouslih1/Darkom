package com.example.paymentsyndecalservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentSyndicatProducerDto {

    private Long relatedId;
    private String message;
    private String senderUsername;
    private Long receivedId;
    private Long agenceId;
    private String token;
    private Long payerId;
}

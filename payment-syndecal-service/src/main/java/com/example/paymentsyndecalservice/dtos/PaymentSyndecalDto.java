package com.example.paymentsyndecalservice.dtos;

import com.example.paymentsyndecalservice.entities.enums.MethodePaymentSyndecal;
import com.example.paymentsyndecalservice.entities.enums.StatusPaymentSyndecal;
import com.example.paymentsyndecalservice.entities.enums.TypePaymentSyndecal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentSyndecalDto {

    private Long id;
    private String description;
    private double montantPaye;
    private TypePaymentSyndecal typePaymentSyndecal;
    private MethodePaymentSyndecal methodePaymentSyndecal;
    private StatusPaymentSyndecal statusPaymentSyndecal;
    private Long agenceId;

    private String agentCreatedBy;

    private String agentUpdatedBy;

    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
}

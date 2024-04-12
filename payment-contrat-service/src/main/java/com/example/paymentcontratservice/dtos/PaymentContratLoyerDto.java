package com.example.paymentcontratservice.dtos;

import com.example.paymentcontratservice.entities.enums.MethodePaymentContratLoyer;
import com.example.paymentcontratservice.entities.enums.StatusPaymentContrat;
import com.example.paymentcontratservice.entities.enums.TypePaymentContratLoyer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentContratLoyerDto {

    private Long id;
    private StatusPaymentContrat statusPaymentContrat;
    private Long contratId;
    private double montantPaye;
    private TypePaymentContratLoyer typePaymentContratLoyer;
    private MethodePaymentContratLoyer methodePaymentContratLoyer;

    private Long agenceId;
    private String agentCreatedBy;
    private String agentUpdatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
}

package com.example.paymentcontratservice.dtos;

import com.example.paymentcontratservice.entities.enums.MethodePaymentContratVente;
import com.example.paymentcontratservice.entities.enums.StatusPaymentContrat;
import com.example.paymentcontratservice.entities.enums.TypePaymentContratVente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentContratVenteDto {

    private Long id;
    private StatusPaymentContrat statusPaymentContrat;
    private Long contratId;
    private double montantPaye;
    private TypePaymentContratVente typePaymentContratVente;
    private MethodePaymentContratVente methodePaymentContratVente;
    private double montantRester;
    private Long agenceId;

    private String agentCreatedBy;
    private String agentUpdatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
}

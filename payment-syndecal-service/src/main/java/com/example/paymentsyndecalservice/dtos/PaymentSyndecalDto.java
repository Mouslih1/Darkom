package com.example.paymentsyndecalservice.dtos;

import com.example.paymentsyndecalservice.entities.enums.MethodePaymentSyndecal;
import com.example.paymentsyndecalservice.entities.enums.StatusPaymentSyndecal;
import com.example.paymentsyndecalservice.entities.enums.TypePaymentSyndecal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentSyndecalDto {

    private Long id;
    @NotBlank
    private String description;
    @NotNull
    private double montantPaye;
    @NotNull
    private TypePaymentSyndecal typePaymentSyndecal;
    @NotNull
    private MethodePaymentSyndecal methodePaymentSyndecal;
    @NotNull
    private StatusPaymentSyndecal statusPaymentSyndecal;
    private Long agenceId;
    @NotNull
    private Long payerId;

    private String agentCreatedBy;

    private String agentUpdatedBy;

    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
}

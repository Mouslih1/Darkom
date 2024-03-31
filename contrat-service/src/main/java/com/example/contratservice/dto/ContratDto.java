package com.example.contratservice.dto;

import com.example.contratservice.entity.enums.TypeContrat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContratDto {

    private Long id;
    private TypeContrat typeContrat;
    @NotNull
    private Date dateSignature;
    @NotNull
    private Long appartementId;
    @NotNull
    private Long propreitaireId;
    private double montant;
    private Long agenceId;

    private String agentCreatedBy;
    private String agentUpdatedBy;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

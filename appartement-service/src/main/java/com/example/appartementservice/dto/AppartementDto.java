package com.example.appartementservice.dto;

import com.example.appartementservice.entity.enums.EtatAppartement;
import com.example.appartementservice.entity.enums.StatusAppartement;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppartementDto {

    private Long id;
    @NotBlank
    private String referenceAppartement;
    @NotNull
    private int numberChambre;
    @NotNull
    private float surface;
    @NotNull
    private double prixLocation;
    @NotNull
    private double prixVente;
    @NotNull
    private StatusAppartement statusAppartement;
    private EtatAppartement etatAppartement = EtatAppartement.LIBRE;
    @NotNull
    private Long immeubleId;
    private String agentCreatedBy;
    private String agentUpdatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

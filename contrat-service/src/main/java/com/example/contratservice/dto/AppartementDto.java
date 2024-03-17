package com.example.contratservice.dto;

import com.example.contratservice.entity.enums.EtatAppartement;
import com.example.contratservice.entity.enums.StatusAppartement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppartementDto {

    private Long id;
    private String referenceAppartement;
    private int numberChambre;
    private float surface;
    private double prixLocation;
    private double prixVente;
    private StatusAppartement statusAppartement;
    private EtatAppartement etatAppartement = EtatAppartement.LIBRE;
    private Long immeubleId;
    private String agentCreatedBy;
    private String agentUpdatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

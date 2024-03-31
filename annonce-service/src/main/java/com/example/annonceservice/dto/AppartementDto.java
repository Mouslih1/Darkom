package com.example.annonceservice.dto;

import com.example.annonceservice.entity.enums.EtatAppartement;
import com.example.annonceservice.entity.enums.StatusAppartement;
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
    private float prixLocation;
    private float prixVente;
    private StatusAppartement statusAppartement;
    private EtatAppartement etatAppartement;
    private Long immeubleId;
    private Long agenceId;

    private String agentCreatedBy;
    private String agentUpdatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

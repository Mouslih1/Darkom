package com.example.annonceservice.dto;

import com.example.annonceservice.entity.enums.StatusAnnonce;
import com.example.annonceservice.entity.enums.TypeAnnonce;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnonceDto {

    private Long id;
    @NotBlank
    private String titre;
    @NotBlank
    private String description;
    @NotBlank
    private Long appartementId;
    @NotNull
    private StatusAnnonce statusAnnonce;
    private TypeAnnonce typeAnnonce;
    private double prixVente;
    private double prixLouer;
    private String agentCreatedBy;
    private String agentUpdatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

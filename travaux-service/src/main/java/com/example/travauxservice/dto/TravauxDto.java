package com.example.travauxservice.dto;

import com.example.travauxservice.entity.enums.Etat;
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
public class TravauxDto {

    private Long id;
    @NotBlank
    private String description;
    @NotNull
    private Etat etat;
    @NotNull
    private Date dateDebut;
    @NotNull
    private Date dateFin;
    @NotNull
    private double montant;
    @NotNull
    private Long immeubleId;
    private String syndecCreatedBy;
    private String syndecUpdatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

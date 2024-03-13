package com.example.travauxservice.dto;

import com.example.travauxservice.entity.enums.Etat;
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
    private String description;
    private Etat etat;
    private Date dateDebut;
    private Date dateFin;
    private double montant;
    private Long immeubleId;
    private String syndecCreatedBy;
    private String syndecUpdatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

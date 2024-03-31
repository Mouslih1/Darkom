package com.example.evenementservice.dto;

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
public class EvenementDto {

    private Long id;
    @NotBlank
    private String sujet;
    @NotBlank
    private String description;
    @NotNull
    private Date dateEvenement;
    @NotNull
    private Long appartementId;
    private Long agenceId;

    private String propreitaireCreatedBy;
    private String propreitaireUpdatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

package com.example.evenementservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @NotBlank
    private Date dateEvenement;
    @NotNull
    private Long appartementId;
    private String propreitaireCreatedBy;
    private String propreitaireUpdatedBy;
    private Date createdAt;
    private Date updatedAt;
}

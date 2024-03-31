package com.example.plainte.dto;

import com.example.plainte.entity.enums.StatusPlainte;
import com.example.plainte.entity.enums.Urgence;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlainteDto {

    private Long id;
    private String sujet;
    private String description;

    private StatusPlainte statusPlainte;
    private Urgence urgence;
    private Long agenceId;

    private String propreitaireCreatedBy;
    private String propreitaireUpdatedBy;

    private Date createdAt;
    private Date updatedAt;
}

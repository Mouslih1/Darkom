package com.example.immeubleservice.dto;

import com.example.immeubleservice.entity.enums.StatusImmeuble;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImmeubleDto {

    private Long id;
    @NotBlank
    private String referenceImmeuble;
    @NotBlank
    private String address;
    @NotNull
    private int numberEtage;
    @NotNull
    private int numberApparetement;
    @NotNull
    private LocalDate anneeConstruction;
    private Long agenceId;
    private StatusImmeuble statusImmeuble = StatusImmeuble.NON_OCCUPER;
    private String agentCreatedBy;
    private String agentUpdatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

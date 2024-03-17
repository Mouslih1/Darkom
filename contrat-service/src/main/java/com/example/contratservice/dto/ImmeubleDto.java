package com.example.contratservice.dto;

import com.example.contratservice.entity.enums.StatusImmeuble;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImmeubleDto {

    private Long id;
    private String referenceImmeuble;
    private String address;
    private int numberEtage;
    private int numberApparetement;
    private Date anneeConstruction;
    private Long agenceId;
    private StatusImmeuble statusImmeuble;
    private String agentCreatedBy;
    private String agentUpdatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

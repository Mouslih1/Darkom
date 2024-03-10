package com.example.immeubleservice.dto;

import com.example.immeubleservice.entity.enums.StatusImmeuble;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

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

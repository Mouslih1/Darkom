package com.example.annonceservice.dto;

import com.example.annonceservice.entity.enums.StatusAnnonce;
import com.example.annonceservice.entity.enums.TypeAnnonce;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnonceRequest {

    private Long id;
    @NotBlank
    private String titre;
    @NotBlank
    private String description;
    @NotNull
    private Long appartementId;
    @NotNull
    private StatusAnnonce statusAnnonce;
    private TypeAnnonce typeAnnonce;
    private double prixVente;
    private double prixLouer;
    private Long agenceId;

    private String agentCreatedBy;
    private String agentUpdatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<MultipartFile> multipartFiles;
}

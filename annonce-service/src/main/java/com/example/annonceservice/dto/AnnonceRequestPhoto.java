package com.example.annonceservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AnnonceRequestPhoto {

    private String agentUpdatedBy;
    private Long agenceId;

    @NotNull
    private List<MultipartFile> multipartFiles;
}

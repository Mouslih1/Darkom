package com.example.agenceservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgenceRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String address;
    @NotBlank
    private String ville;
    @NotBlank
    private String telephone;
    @NotBlank
    @Email
    private String email;
    private String agentCreatedBy;
    private String agentUpdatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<MultipartFile> multipartFiles;
}

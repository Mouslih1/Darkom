package com.example.userservice.dto;

import com.example.userservice.entity.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotBlank
    private String username;
    @NotBlank
    private String email;
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @NotBlank
    private String address;
    @NotBlank
    private String ville;
    @NotBlank
    private String telephone;
    @NotNull
    private Role role;
    private Long agentCreatedBy;
    private Long agenceId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @NotNull
    private LocalDate dateNaissance;
    private List<MultipartFile> multipartFiles;
}
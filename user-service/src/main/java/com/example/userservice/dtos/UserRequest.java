package com.example.userservice.dtos;

import com.example.userservice.entities.enums.Role;
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

    private Long id;
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
    private String password;
    @NotBlank
    private String telephone;
    @NotNull
    private Role role;
    private String agentCreatedBy;
    private String agentUpdatedBy;
    private Long agenceId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @NotNull
    private LocalDate dateNaissance;
    @NotNull
    private List<MultipartFile> multipartFiles;
}

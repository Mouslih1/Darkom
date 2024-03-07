package com.example.userservice.dto;

import com.example.userservice.entity.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private String address;
    private String ville;
    private String telephone;
    private Role role;
    private Long agentCreatedBy;
    private Long agenceId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDate dateNaissance;
}

package com.example.userservice.dto;

import com.example.userservice.entity.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

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
    @NotBlank
    private String ville;
    @NotBlank
    private String telephone;
    @NotNull
    private Role role;
    private Long agentCreatedBy;
    private LocalDateTime createdAt;
    @NotNull
    private LocalDateTime dateNaissance;
}

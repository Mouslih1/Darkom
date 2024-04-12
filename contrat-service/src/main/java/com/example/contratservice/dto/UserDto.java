package com.example.contratservice.dto;

import com.example.contratservice.entity.enums.Role;
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
    private String password;
    private String telephone;
    private Role role;
    private String agentCreatedBy;
    private String agentUpdatedBy;
    private Long agenceId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDate dateNaissance;
}

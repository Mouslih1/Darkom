package com.example.agenceservice.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AgenceDto {

    private Long id;
    private String name;
    private String address;
    private String ville;
    private String telephone;
    private String email;
    private Long agentCreatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

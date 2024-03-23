package com.example.userservice.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPasswordDto {

    @NotBlank
    private String currentPassword;
    @NotBlank
    private String newPassword;
}

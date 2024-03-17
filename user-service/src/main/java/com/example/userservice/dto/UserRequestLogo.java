package com.example.userservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestLogo {

    private String agentUpdatedBy;
    @NotNull
    private List<MultipartFile> multipartFiles;
}

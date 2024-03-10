package com.example.userservice.dto;

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
    private List<MultipartFile> multipartFiles;
}

package com.example.userservice.dto;

import com.example.userservice.entity.enums.MediaStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MediaDto {

    private Long id;
    private String mediaUuid;
    private String filename;
    private String agentCreatedBy;
    private String agentUpdatedBy;
    private String uri;
    private Long relatedId;
    private MediaStatus mediaStatus;
    private String fileType;
    private Long size;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
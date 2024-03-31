package com.example.agenceservice.dto;

import com.example.agenceservice.entity.enums.MediaStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MediaDto {

    private Long id;
    private String mediaUuid;
    private String filename;
    private String uri;
    private Long relatedId;
    private MediaStatus mediaStatus;
    private String fileType;
    private Long size;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
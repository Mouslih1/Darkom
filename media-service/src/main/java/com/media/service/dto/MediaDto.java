package com.media.service.dto;

import com.media.service.model.Media;
import com.media.service.model.enums.MediaStatus;
import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO for {@link Media}
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MediaDto {

    private Long id;
    private String mediaUuid;
    private String filename;
    private Long agentCreatedBy;
    private String uri;
    private Long relatedId;
    private MediaStatus mediaStatus;
    private String fileType;
    private Long size;
    private LocalDateTime createdDate;
}
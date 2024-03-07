package com.media.service.model;

import com.media.service.model.enums.MediaStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import  jakarta.persistence.Id;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "medias")
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE medias SET is_delete = true WHERE id=?")
@Where(clause = "is_delete = false")
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filename;
    private String mediaUuid;
    private Long agentCreatedBy;
    private String uri;
    private Long relatedId;
    @Enumerated(EnumType.STRING)
    private MediaStatus mediaStatus;
    private String fileType;
    private Long size;
    @CreatedDate
    private LocalDateTime createdDate;
    private boolean isDelete = Boolean.FALSE;
}
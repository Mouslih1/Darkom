package com.example.notificationservice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {

    private String message;
    private Long relatedId;
    private Long receivedId;
    private String senderUsername;
    private Long agenceId;
    private LocalDateTime createdAt;
    private boolean seen = Boolean.FALSE;
}

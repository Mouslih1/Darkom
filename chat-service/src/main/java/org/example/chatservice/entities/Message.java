package org.example.chatservice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.chatservice.entities.enums.StatusMessage;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE messages SET is_delete = true WHERE id=?")
@Where(clause = "is_delete = false")
@Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long senderId;
    private Long receivedId;
    private String content;
    private StatusMessage statusMessage;

    @CreatedDate
    private LocalDateTime createdAt;
    private boolean isDelete = Boolean.FALSE;
}

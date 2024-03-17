package com.example.travauxservice.entity;


import com.example.travauxservice.entity.enums.Etat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "travaux")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE travaux SET is_delete = true WHERE id=?")
@Where(clause = "is_delete = false")
@EntityListeners(AuditingEntityListener.class)
public class Travaux {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @Enumerated(EnumType.STRING)
    private Etat etat;
    private Date dateDebut;
    private Date dateFin;
    private double montant;
    private Long immeubleId;
    @CreatedBy
    private String syndecCreatedBy;
    @LastModifiedBy
    private String syndecUpdatedBy;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    private boolean isDelete = Boolean.FALSE;
}

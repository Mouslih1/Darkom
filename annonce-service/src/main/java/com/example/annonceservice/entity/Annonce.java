package com.example.annonceservice.entity;

import com.example.annonceservice.entity.enums.StatusAnnonce;
import com.example.annonceservice.entity.enums.TypeAnnonce;
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

@Setter
@Getter
@ToString
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE annonces SET is_delete  = true WHERE id = ?")
@Where(clause = "is_delete = false")
@Entity
@Table(name = "annonces")
public class Annonce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String description;
    private Long appartementId;
    @Enumerated(EnumType.STRING)
    private StatusAnnonce statusAnnonce;
    @Enumerated(EnumType.STRING)
    private TypeAnnonce typeAnnonce;
    private double prixVente;
    private double prixLouer;
    @CreatedBy
    private String agentCreatedBy;
    @LastModifiedBy
    private String agentUpdatedBy;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    private boolean isDelete = Boolean.FALSE;
}

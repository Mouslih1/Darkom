package com.example.appartementservice.entity;

import com.example.appartementservice.entity.enums.EtatAppartement;
import com.example.appartementservice.entity.enums.StatusAppartement;
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

@Entity
@Table(name = "appartements")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE appartements SET is_delete = true WHERE id=?")
@Where(clause = "is_delete = false")
public class Appartement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String referenceAppartement;
    private int numberChambre;
    private double surface;
    private double prixLocation;
    private double prixVente;
    @Enumerated(value = EnumType.STRING)
    private StatusAppartement statusAppartement;
    @Enumerated(value = EnumType.STRING)
    private EtatAppartement etatAppartement;
    private Long immeubleId;
    private Long proprietaireId;
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
